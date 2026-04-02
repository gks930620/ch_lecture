package ch15_입출력IO;

/**
 * 📝 과제: 파일 관리 시스템
 * 
 * 난이도: ★★★★☆
 * 
 * 문제:
 * 파일 입출력을 활용한 간단한 메모장 프로그램을 작성하세요.
 * 
 * [FileManager 클래스]
 * - 메소드:
 *   1. writeToFile(String filename, String content): 파일에 내용 쓰기
 *   2. readFromFile(String filename): 파일 내용 읽기
 *   3. appendToFile(String filename, String content): 파일에 내용 추가
 *   4. copyFile(String source, String destination): 파일 복사
 *   5. deleteFile(String filename): 파일 삭제
 *   6. fileExists(String filename): 파일 존재 여부 확인
 *   7. getFileInfo(String filename): 파일 정보 조회 (크기, 수정일 등)
 * 
 * [LogManager 클래스]
 * - 로그 파일 관리
 * - 메소드:
 *   1. writeLog(String message): 시간과 함께 로그 기록
 *   2. readLogs(): 모든 로그 읽기
 *   3. clearLogs(): 로그 파일 초기화
 * 
 * [StudentFileManager 클래스]
 * - 학생 데이터를 파일로 관리
 * - 메소드:
 *   1. saveStudents(List<Student> students): 학생 목록을 CSV로 저장
 *   2. loadStudents(): CSV 파일에서 학생 목록 불러오기
 *   3. addStudent(Student student): 학생 추가 (파일에 append)
 * 
 * [구현할 기능]
 * 1. 텍스트 파일 읽기/쓰기
 * 2. 파일 복사 및 삭제
 * 3. CSV 파일 처리
 * 4. 로그 파일 관리
 * 5. 예외 처리 (파일이 없을 때, 권한 없을 때 등)
 * 
 * 예상 출력:
 * ===== 파일 관리 시스템 =====
 * 
 * [파일 쓰기]
 * ✅ memo.txt 파일에 내용을 작성했습니다.
 * 
 * [파일 읽기]
 * === memo.txt 내용 ===
 * 안녕하세요!
 * 이것은 테스트 메모입니다.
 * 
 * [파일 정보]
 * 파일명: memo.txt
 * 크기: 48 bytes
 * 마지막 수정: 2026-02-13 10:30:45
 * 
 * [파일 복사]
 * ✅ memo.txt를 memo_backup.txt로 복사했습니다.
 * 
 * [로그 기록]
 * ✅ 로그 기록: 프로그램 시작
 * ✅ 로그 기록: 파일 생성 완료
 * ✅ 로그 기록: 파일 읽기 완료
 * 
 * [로그 조회]
 * === app.log 내용 ===
 * [2026-02-13 10:30:45] 프로그램 시작
 * [2026-02-13 10:30:46] 파일 생성 완료
 * [2026-02-13 10:30:47] 파일 읽기 완료
 * 
 * [학생 데이터 저장]
 * ✅ 3명의 학생 데이터를 students.csv에 저장했습니다.
 * 
 * [학생 데이터 불러오기]
 * === students.csv 내용 ===
 * ID,이름,나이,성적
 * 1,홍길동,20,85.5
 * 2,김철수,22,90.0
 * 3,이영희,21,88.5
 * 
 * [에러 처리]
 * ❌ 오류: notfound.txt 파일을 찾을 수 없습니다.
 */
public class 과제_파일관리 {
    
    // TODO: FileManager 클래스를 작성하세요
    
    // TODO: LogManager 클래스를 작성하세요
    
    // TODO: StudentFileManager 클래스를 작성하세요
    
    public static void main(String[] args) {
        // TODO: 테스트 코드 작성
        
    }
}

