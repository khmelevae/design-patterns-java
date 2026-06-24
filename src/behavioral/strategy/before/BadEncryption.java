package behavioral.strategy.before;

// Плохая реализация: вместо стратегий используется перечисление и switch
public class BadEncryption {
    // Глобальный флаг, легко ломается из любого места
    private static int currentCipher = 0; // 0 - Caesar, 1 - Reverse, 2 - XOR
    private static int caesarShift = 3;
    private static int xorKey = 42;

    // Слишком большие методы, делают всё сразу
    public static String encrypt(String input, int cipherType) {
        switch (cipherType) {
            case 0:
                return caesarShift(input, caesarShift);
            case 1:
                return new StringBuilder(input).reverse().toString();
            case 2:
                return xorProcess(input, xorKey);
            default:
                throw new IllegalArgumentException("Unknown cipher");
        }
    }

    public static String decrypt(String input, int cipherType) {
        switch (cipherType) {
            case 0:
                return caesarShift(input, -caesarShift);
            case 1:
                return new StringBuilder(input).reverse().toString();
            case 2:
                return xorProcess(input, xorKey);
            default:
                throw new IllegalArgumentException("Unknown cipher");
        }
    }

    // Дублируется логика из CaesarCipher
    private static String caesarShift(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shifted = (c - base + shift) % 26;
                if (shifted < 0) shifted += 26;
                result.append((char) (base + shifted));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Дублируется логика из XorCipher
    private static String xorProcess(String input, int key) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ key);
        }
        return new String(chars);
    }

    // Управляется через статические переменные - антипаттерн
    public static void setCaesarShift(int shift) {
        caesarShift = shift;
    }
    public static void setXorKey(int key) {
        xorKey = key;
    }

    // Геттеры/сеттеры
    public static void setCurrentCipher(int type) {
        currentCipher = type;
    }
    public static int getCurrentCipher() {
        return currentCipher;
    }
}