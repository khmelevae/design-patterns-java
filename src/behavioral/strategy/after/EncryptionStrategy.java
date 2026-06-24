package behavioral.strategy.after;

public interface EncryptionStrategy {
    String encrypt(String input);
    String decrypt(String input);
}
