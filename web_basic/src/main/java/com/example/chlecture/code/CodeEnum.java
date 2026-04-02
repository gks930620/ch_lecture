package com.example.chlecture.code;

/**
 * [Ch12 참고용] 범용 코드 Enum 예시 — 실제 프로젝트에서는 사용하지 않음.
 * 실무에서는 BoardStatus처럼 도메인별로 분리하는 것이 좋다.
 * @see BoardStatus — 실습에서 실제 사용하는 Enum
 */
public enum CodeEnum {
    STATUS_ACTIVE("A", "활성"),
    STATUS_INACTIVE("I", "비활성");

    private final String code;
    private final String desc;

    CodeEnum(String code, String desc) { this.code = code; this.desc = desc; }
    public String getCode() { return code; }
    public String getDesc() { return desc; }
}

