package com.example.chatapp.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {
  @NotBlank
  private String clientSessionId;
}
