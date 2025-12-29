package com.example.chatapp.response;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OnlineUsersResponse {
  private Integer count;
  private Set<String> users;
}
