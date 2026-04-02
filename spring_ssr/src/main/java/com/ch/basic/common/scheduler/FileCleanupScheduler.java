package com.ch.basic.common.scheduler;

import com.ch.basic.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 고아 파일 정리 스케줄러
 *
 * refId가 0(연결된 게시글 없음)인 상태로 일정 시간이 지난 파일들을
 * 주기적으로 삭제하여 디스크 및 DB 공간을 확보한다.
 */
@Component
@RequiredArgsConstructor
public class FileCleanupScheduler {

    private final FileService fileService;

    // 매일 새벽 4시에 실행 (cron = "초 분 시 일 월 요일")
    @Scheduled(cron = "0 0 4 * * *")
    public void cleanupOrphanFiles() {
        // 기준 시간: 24시간 이전 (즉, 생성/수정된 지 24시간이 지난 고아 파일만 삭제)
        // 에디터 작성 중인 파일이 바로 삭제되지 않도록 유예 기간을 둠
        LocalDateTime timeLimit = LocalDateTime.now().minusHours(24);

        int count = fileService.deleteOrphanFiles(timeLimit);

        // 로그가 없으므로 출력하지 않음 (System.out.println 등 사용 가능하지만 생략)
    }
}

