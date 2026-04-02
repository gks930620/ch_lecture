package com.example.chlecture.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 웹 애플리케이션 시작/종료 시 호출되는 리스너.
 * Spring의 ContextLoaderListener가 바로 이 인터페이스의 구현체이다.
 */
@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[AppInitListener] ===== 웹앱 시작됨 =====");
        System.out.println("[AppInitListener] contextPath: " + sce.getServletContext().getContextPath());
        // 실무: DB 커넥션 풀 초기화, 설정 파일 로드, 스케줄러 시작 등
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppInitListener] ===== 웹앱 종료됨 =====");
        // 실무: 자원 정리, 커넥션 풀 해제
    }
}

