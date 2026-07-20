package com.test.test.stomp.service;

import com.test.test.common.exception.EntityNotFoundException;
import com.test.test.stomp.entity.RoomEntity;
import com.test.test.stomp.model.RoomDTO;
import com.test.test.stomp.repository.RoomRepository;
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
            .orElseThrow(() -> EntityNotFoundException.of("Room", roomId));
        return RoomDTO.from(entity);
    }

    @Transactional
    public RoomDTO createRoom(String rawName) {
        String roomName = rawName == null ? "" : rawName.trim();
        if (roomName.isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be empty");
        }
        if (roomRepository.existsByNameIgnoreCase(roomName)) {
            throw new IllegalArgumentException("Room name already exists");
        }

        RoomEntity saved = roomRepository.save(new RoomEntity(null, roomName));
        return RoomDTO.from(saved);
    }
}

