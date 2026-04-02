package com.ch.basic.chat;

import com.ch.basic.chat.dto.ChatRoomCreateRequest;
import com.ch.basic.chat.dto.ChatRoomResponse;
import com.ch.basic.user.CustomUserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 채팅방 SSR 컨트롤러
 *
 * 채팅방 목록 조회, 생성, 입장 페이지를 Thymeleaf로 렌더링.
 * 보안은 이 RequestMapping 수준에서만 적용 (SecurityConfig에서 /chat/** authenticated).
 * WebSocket 통신 자체에는 별도 보안 미적용.
 *
 * 프로젝트 원칙:
 *   - Controller는 요청/응답 처리만 담당
 *   - Repository, Entity 직접 사용 금지 → Service를 통해서만 접근
 *   - @AuthenticationPrincipal로 인증 사용자 정보 주입
 */
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅방 목록 페이지
     * GET /chat/rooms
     */
    @GetMapping("/rooms")
    public String rooms(@AuthenticationPrincipal CustomUserAccount userDetails, Model model) {
        List<ChatRoomResponse> rooms = chatService.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("user", userDetails);
        return "chat/rooms";
    }

    /**
     * 채팅방 생성 처리
     * POST /chat/rooms → 생성 후 목록으로 redirect
     */
    @PostMapping("/rooms")
    public String createRoom(@AuthenticationPrincipal CustomUserAccount userDetails,
                             ChatRoomCreateRequest request) {
        chatService.createRoom(request, userDetails.getNickname());
        return "redirect:/chat/rooms";
    }

    /**
     * 채팅방 입장 페이지
     * GET /chat/rooms/{roomId}
     *
     * model에 room 정보와 nickname을 담아서 Thymeleaf에서 JS 변수로 바인딩
     * → SockJS/STOMP 연결 시 사용
     */
    @GetMapping("/rooms/{roomId}")
    public String enterRoom(@PathVariable Long roomId,
                            @AuthenticationPrincipal CustomUserAccount userDetails,
                            Model model) {
        ChatRoomResponse room = chatService.getRoomById(roomId);
        model.addAttribute("room", room);
        model.addAttribute("user", userDetails);
        return "chat/room";
    }
}

