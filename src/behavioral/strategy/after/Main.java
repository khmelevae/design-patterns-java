package behavioral.strategy.after;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        EncryptionContext context = new EncryptionContext();
        String originalText = "Hello, World! 123";

        System.out.println("Исходная строка: " + originalText);
        System.out.println(String.join("", Collections.nCopies(50, "-")));

        // Тестируем Цезаря
        CaesarCipher caesar = new CaesarCipher(3);
        context.setStrategy(caesar);
        test(context, originalText, "ЦЕЗАРЬ (сдвиг 3)");

        // Тестируем Reverse
        ReverseCipher reverse = new ReverseCipher();
        context.setStrategy(reverse);
        test(context, originalText, "REVERSE");

        // Тестируем XOR
        XorCipher xor = new XorCipher(42);
        context.setStrategy(xor);
        test(context, originalText, "XOR (ключ 42)");
    }

    private static void test(EncryptionContext context, String original, String cipherName) {
        String encrypted = context.encrypt(original);
        String decrypted = context.decrypt(encrypted);

        System.out.println(cipherName + ":");
        System.out.println("  Зашифровано: " + encrypted);
        System.out.println("  Расшифровано: " + decrypted);
        System.out.println("  Успешно: " + original.equals(decrypted));
        System.out.println();
    }
}
