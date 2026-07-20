package com.test.test.stomp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomCreateRequest(
    @NotBlank(message = "Room name is required")
    @Size(max = 50, message = "Room name must be at most 50 characters")
    String name
) {
}

