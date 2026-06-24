package behavioral.strategy.before;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        String originalText = "Hello, World! 123";

        System.out.println("Исходная строка: " + originalText);
        System.out.println(String.join("", Collections.nCopies(50, "-")));

        // Caesar
        BadEncryption.setCurrentCipher(0);
        BadEncryption.setCaesarShift(3);
        test(originalText, "ЦЕЗАРЬ (сдвиг 3)");

        // Reverse
        BadEncryption.setCurrentCipher(1);
        test(originalText, "REVERSE");

        // XOR
        BadEncryption.setCurrentCipher(2);
        BadEncryption.setXorKey(42);
        test(originalText, "XOR (ключ 42)");
    }

    private static void test(String original, String cipherName) {
        int cipherType = BadEncryption.getCurrentCipher();

        String encrypted = BadEncryption.encrypt(original, cipherType);
        String decrypted = BadEncryption.decrypt(encrypted, cipherType);

        System.out.println(cipherName + ":");
        System.out.println("  Зашифровано: " + encrypted);
        System.out.println("  Расшифровано: " + decrypted);
        System.out.println("  Успешно: " + original.equals(decrypted));
        System.out.println();
    }
}
