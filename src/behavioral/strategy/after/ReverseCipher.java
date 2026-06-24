package behavioral.strategy.after;

public class ReverseCipher implements EncryptionStrategy {
    @Override
    public String encrypt(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    @Override
    public String decrypt(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
