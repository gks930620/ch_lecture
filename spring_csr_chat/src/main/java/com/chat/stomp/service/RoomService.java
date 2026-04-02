package com.chat.stomp.service;

import com.chat.stomp.entity.RoomEntity;
import com.chat.stomp.model.RoomDTO;
import com.chat.stomp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
            .map(RoomDTO::from)
            .toList();
    }

    public RoomDTO getRoom(Long roomId) {
        RoomEntity entity = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 방입니다."));
        return RoomDTO.from(entity);
    }
}

