package com.example.chlecture.code;

/**
 * 게시글 상태 코드 Enum
 * DB에는 "A", "I", "D" 문자열로 저장되고, Java에서는 이 Enum으로 안전하게 다룬다.
 */
public enum BoardStatus {
    ACTIVE("A", "활성"),
    INACTIVE("I", "비활성"),
    DELETED("D", "삭제");

    private final String code;
    private final String label;

    BoardStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }

    /**
     * DB에서 읽은 코드 문자열 → Enum 변환
     * @param code "A", "I", "D"
     * @return 대응하는 BoardStatus
     * @throws IllegalArgumentException 알 수 없는 코드
     */
    public static BoardStatus fromCode(String code) {
        for (BoardStatus s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown BoardStatus code: " + code);
    }
}

