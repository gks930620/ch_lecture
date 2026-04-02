package com.example.chlecture.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 세션 생성/소멸을 추적하여 현재 활성 세션 수를 관리하는 리스너.
 */
@WebListener
public class SessionCountListener implements HttpSessionListener {
    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = activeSessions.incrementAndGet();
        System.out.println("[SessionCount] 세션 생성 (ID: " + se.getSession().getId() + ") 현재 세션 수: " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = activeSessions.decrementAndGet();
        System.out.println("[SessionCount] 세션 소멸 (ID: " + se.getSession().getId() + ") 현재 세션 수: " + count);
    }

    public static int getActiveSessions() {
        return activeSessions.get();
    }
}

