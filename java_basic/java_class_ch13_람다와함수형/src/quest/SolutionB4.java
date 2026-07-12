package quest;

import java.util.UUID;
import java.util.function.Supplier;

// B-4: Supplier<UUID>로 랜덤 ID 생성 (입력 없이 값을 공급, 호출마다 새 값 = 지연 생성)
public class SolutionB4 {
    public static void main(String[] args) {
        Supplier<UUID> idGen = UUID::randomUUID;
        System.out.println(idGen.get()); // 매번 다른 UUID
        System.out.println(idGen.get()); // 매번 다른 UUID
    }
}
