package com.test.test.stomp.controller;

import com.test.test.stomp.model.RoomCreateRequest;
import com.test.test.stomp.model.RoomDTO;
import com.test.test.stomp.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 전체 방 목록
    @GetMapping("/rooms")
    public List<RoomDTO> getRooms() {
        return roomService.getAllRooms();
    }

    // 특정 방 상세
    @GetMapping("/room/{roomId}")
    public RoomDTO getRoom(@PathVariable Long roomId) {
        return roomService.getRoom(roomId);
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    public RoomDTO createRoom(@Valid @RequestBody RoomCreateRequest request) {
        return roomService.createRoom(request.name());
    }
}

