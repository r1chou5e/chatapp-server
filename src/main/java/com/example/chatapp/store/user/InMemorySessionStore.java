package com.example.chatapp.store.user;

import com.example.chatapp.object.session.SessionInfo;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class InMemorySessionStore implements SessionStore {

  // stompSessionId -> SessionInfo
  private final ConcurrentHashMap<String, SessionInfo> sessionInfoMap = new ConcurrentHashMap<>();

  // clientSessionId -> stompSessionId
  private final ConcurrentHashMap<String, String> clientSessionMap = new ConcurrentHashMap<>();

  @Override
  public void add(String clientSessionId, String stompSessionId, String username) {
    SessionInfo info = new SessionInfo(stompSessionId, clientSessionId, username);
    sessionInfoMap.put(stompSessionId, info);
    clientSessionMap.put(clientSessionId, stompSessionId);
  }

  @Override
  public SessionInfo remove(String stompSessionId) {
    SessionInfo removed = sessionInfoMap.remove(stompSessionId);
    if (removed == null) {
      return null;
    }

    String clientSessionId = removed.getClientSessionId();

    clientSessionMap.computeIfPresent(
        clientSessionId,
        (key, mappedStompSessionId) ->
            mappedStompSessionId.equals(stompSessionId) ? null : mappedStompSessionId
    );

    return removed;
  }

  @Override
  public Set<String> getOnlineUsers() {
    return Set.copyOf(sessionInfoMap.values().stream().map(SessionInfo::getUsername).collect(
        Collectors.toSet()));
  }

  @Override
  public String getStompSessionIdByClientSessionId(String clientSessionId) {
    return clientSessionMap.get(clientSessionId);
  }

  @Override
  public Set<String> getSessionsByUsername(String username) {
    return sessionInfoMap.entrySet()
        .stream()
        .filter(entry -> entry.getValue().getUsername().equals(username))
        .map(Entry::getKey) // sessionId
        .collect(Collectors.toSet());
  }

  @Override
  public String getUsername(String sessionId) {
    SessionInfo info = sessionInfoMap.get(sessionId);
    if (info != null) return info.getUsername();
    return null;
  }
}
