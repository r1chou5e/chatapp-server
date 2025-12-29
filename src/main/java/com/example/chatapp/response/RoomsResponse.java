package com.example.chatapp.response;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomsResponse {
  private Set<String> rooms;
  private Integer count;
}
