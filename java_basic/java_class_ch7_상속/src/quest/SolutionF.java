package quest;

// F-1. 챌린지 — 게임 캐릭터 계층과 스킬 오버라이딩
// 부모 이름은 java.lang.Character와 겹치지 않도록 GameCharacter로 짓는다.
public class SolutionF {

    static abstract class GameCharacter {
        protected final String name;
        protected int hp;

        GameCharacter(String name, int hp) {
            this.name = name;
            this.hp = hp;
        }

        // 공통 기본 공격
        void attack() {
            System.out.println(name + ": 기본 공격!");
        }

        // 직업별로 반드시 다른 스킬
        abstract void useSkill();
    }

    static class Warrior extends GameCharacter {
        Warrior(String name) { super(name, 150); }

        @Override
        void useSkill() {
            System.out.println(name + ": [강타] 방패로 내려찍는다!");
        }
    }

    static class Mage extends GameCharacter {
        Mage(String name) { super(name, 80); }

        @Override
        void useSkill() {
            System.out.println(name + ": [파이어볼] 화염구를 날린다!");
        }

        @Override
        void attack() { // 기본 공격도 재정의 가능
            System.out.println(name + ": 지팡이 공격!");
        }
    }

    public static void main(String[] args) {
        GameCharacter[] party = { new Warrior("전사A"), new Mage("마법사B") };

        for (GameCharacter c : party) {
            c.attack();
            c.useSkill();
        }
        // 전사A: 기본 공격!
        // 전사A: [강타] 방패로 내려찍는다!
        // 마법사B: 지팡이 공격!
        // 마법사B: [파이어볼] 화염구를 날린다!
    }
}
