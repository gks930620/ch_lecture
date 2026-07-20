package com.test.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
	{
		// .env 파일 로드
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		// 환경 변수 로드 확인 로그 (선택사항)
		// ⚠️ 민감정보(CLIENT_ID/SECRET/KEY) 실제 값은 콘솔·로그에 찍지 않는다. "설정됨/설정안됨"만 확인.
		System.out.println("✅ .env 파일 로드 완료");
		System.out.println("KAKAO_CLIENT_ID: " + (context.getEnvironment().getProperty("KAKAO_CLIENT_ID") != null ? "설정됨" : "설정안됨"));
		System.out.println("GOOGLE_CLIENT_ID: " + (context.getEnvironment().getProperty("GOOGLE_CLIENT_ID") != null ? "설정됨" : "설정안됨"));
		System.out.println("JWT_SECRET_KEY: " + (context.getEnvironment().getProperty("JWT_SECRET_KEY") != null ? "설정됨" : "설정안됨"));
	}

}
