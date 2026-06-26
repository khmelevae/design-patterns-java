package behavioral.command.after.model;

public class Character {
    private String name;
    private int health;
    private int maxHealth;
    private boolean isBlocking; // Находится ли персонаж в блоке
    private int specialCooldown; // Время перезарядки специальной атаки
    private int currentCooldown; // Текущее время перезарядки
    private int damage;
    private int specialDamage;

    public Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.isBlocking = false;
        this.specialCooldown = 3;
        this.currentCooldown = 0;
        this.damage = 20;
        this.specialDamage = 35;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getDamage() { return damage; }
    public int getSpecialDamage() {return specialDamage; }
    public boolean isBlocking() { return isBlocking; }
    public int getCurrentCooldown() { return currentCooldown; }
    public void setBlocking(boolean _isBlocking) { isBlocking = _isBlocking; }
    public boolean canUseSpecial() { return currentCooldown == 0; }

    public void setHealth(int _health) { this.health = _health; }
    public void setCurrentCooldown(int _currentCooldown) { this.currentCooldown = _currentCooldown; }

    public int takeDamage(int incomingDamage) {
        int actualDamage = incomingDamage;
        if (isBlocking) {
            actualDamage = incomingDamage / 2;
            System.out.println(name + " блокирует атаку и получает " + actualDamage + " урона");
        }

        health -= actualDamage;
        if (health < 0) health = 0;
        return actualDamage;
    }

    public int takeSpecialDamage(int incomingSpecialDamage) {
        int actualSpecialDamage = incomingSpecialDamage;
        if (isBlocking) {
            actualSpecialDamage = incomingSpecialDamage / 3;
            System.out.println(name + " блокирует специальную атаку и получает " + actualSpecialDamage + " урона");
        }

        health -= actualSpecialDamage;
        if (health < 0) health = 0;
        return actualSpecialDamage;
    }

    public void decreaseCooldown() { if (currentCooldown > 0) currentCooldown--; }

    public void useSpecial() { currentCooldown = specialCooldown; }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;

        System.out.println(name + " восстанавливает " + amount + " здоровья");
    }

    public boolean isAlive() { return (health > 0); }

    public void resetBlock() { isBlocking = false; }

    @Override
    public String toString() {
        return (name + ": " + health + " из " + maxHealth + " здоровья" +
                (currentCooldown > 0 ? " (Специальная атака через " + currentCooldown + " хода)" : ""));
    }
}
