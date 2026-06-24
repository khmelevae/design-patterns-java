package behavioral.strategy.after;

public class CaesarCipher implements EncryptionStrategy {
    private final int shift;

    public CaesarCipher(int shift) {
        this.shift = shift;
    }

    @Override
    public String encrypt(String input) {
        return caesarShift(input, shift);
    }

    @Override
    public String decrypt(String input) {
        return caesarShift(input, -shift);
    }

    private String caesarShift(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shifted = (c - base + shift) % 26;
                if (shifted < 0) shifted += 26;
                result.append((char)(base + shifted));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
