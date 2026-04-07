
import java.util.Arrays;

public class Part3_ArrayCopy {
    public static void main(String[] args) {
        System.out.println("=== 얕은 복사 vs 깊은 복사 ===");

        int[] original = {1, 2, 3, 4, 5};

        // 얕은 복사 (참조 복사)
        System.out.println("\n1. 얕은 복사 (참조만 복사)");
        int[] shallow = original;
        shallow[0] = 100;
        System.out.println("original[0]: " + original[0]);  // 100 (같이 변경됨!)
        System.out.println("shallow[0]: " + shallow[0]);    // 100

        // 다시 원상복구
        original[0] = 1;

        // 깊은 복사 1: 반복문
        System.out.println("\n2. 깊은 복사 - 반복문");
        int[] deep1 = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            deep1[i] = original[i];
        }
        deep1[0] = 200;
        System.out.println("original: " + Arrays.toString(original));
        System.out.println("deep1: " + Arrays.toString(deep1));

        // 깊은 복사 2: System.arraycopy
        System.out.println("\n3. 깊은 복사 - System.arraycopy");
        int[] deep2 = new int[original.length];
        System.arraycopy(original, 0, deep2, 0, original.length);
        deep2[0] = 300;
        System.out.println("original: " + Arrays.toString(original));
        System.out.println("deep2: " + Arrays.toString(deep2));

        // 깊은 복사 3: Arrays.copyOf
        System.out.println("\n4. 깊은 복사 - Arrays.copyOf");
        int[] deep3 = Arrays.copyOf(original, original.length);
        deep3[0] = 400;
        System.out.println("original: " + Arrays.toString(original));
        System.out.println("deep3: " + Arrays.toString(deep3));

        // 깊은 복사 4: clone
        System.out.println("\n5. 깊은 복사 - clone");
        int[] deep4 = original.clone();
        deep4[0] = 500;
        System.out.println("original: " + Arrays.toString(original));
        System.out.println("deep4: " + Arrays.toString(deep4));

        // 부분 복사
        System.out.println("\n6. 부분 복사");
        int[] partial = Arrays.copyOfRange(original, 1, 4);  // 인덱스 1~3
        System.out.println("partial: " + Arrays.toString(partial));
    }
}

