package behavioral.strategy.after;

public class XorCipher implements EncryptionStrategy {
    private final int key;

    public XorCipher(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String input) {
        return xorProcess(input);
    }

    @Override
    public String decrypt(String input) {
        return xorProcess(input);
    }

    private String xorProcess(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char)(chars[i] ^ key);
        }
        return new String(chars);
    }
}
