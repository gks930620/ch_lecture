package com.chat.stomp.controller;

import com.chat.stomp.model.RoomDTO;
import com.chat.stomp.service.RoomService;
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

}
