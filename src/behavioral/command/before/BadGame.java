package behavioral.command.before;

import java.util.*;

// BadGame.java - Анти-паттерн: всё в одном классе без Command
public class BadGame {
    private String player1Name;
    private String player2Name;
    private int p1Health;
    private int p2Health;
    private int p1MaxHealth;
    private int p2MaxHealth;
    private boolean p1Blocking;
    private boolean p2Blocking;
    private int p1Cooldown;
    private int p2Cooldown;
    private int p1Damage = 20;
    private int p2Damage = 20;
    private int p1SpecialDamage = 35;
    private int p2SpecialDamage = 35;
    private int p1SpecialCooldown = 3;
    private int p2SpecialCooldown = 3;

    private Scanner scanner;
    private int turn = 0;
    private int currentAttacker = 1; // 1 или 2
    private boolean gameOver = false;

    // История для отмены ходов (храним в виде строк, грязный подход)
    private Stack<String> history = new Stack<>();

    public BadGame() {
        scanner = new Scanner(System.in);
        initGame();
    }

    private void initGame() {
        System.out.println("СМЕРТЕЛЬНАЯ БИТВА (ПЛОХАЯ ВЕРСИЯ)\n");
        System.out.print("Имя 1-го бойца: ");
        player1Name = scanner.nextLine();
        System.out.print("Имя 2-го бойца: ");
        player2Name = scanner.nextLine();

        p1Health = p1MaxHealth = 100;
        p2Health = p2MaxHealth = 100;
        p1Blocking = p2Blocking = false;
        p1Cooldown = p2Cooldown = 0;
        currentAttacker = 1;

        System.out.println("\nНачало боя!");
    }

    // Кошмар: один метод делает ВСЁ!
    private void processTurn() {
        while (!gameOver) {
            System.out.println("\nХод №" + (turn + 1));
            System.out.println(player1Name + ": " + p1Health + "/" + p1MaxHealth +
                    (p1Cooldown > 0 ? " (перезарядка " + p1Cooldown + ")" : ""));
            System.out.println(player2Name + ": " + p2Health + "/" + p2MaxHealth +
                    (p2Cooldown > 0 ? " (перезарядка " + p2Cooldown + ")" : ""));

            String attackerName = (currentAttacker == 1) ? player1Name : player2Name;
            String defenderName = (currentAttacker == 1) ? player2Name : player1Name;
            System.out.println("\nАтакует: " + attackerName);

            System.out.println("\nДействия:");
            System.out.println("1. Удар");
            System.out.println("2. Блок");
            System.out.println("3. Специальная атака");
            System.out.println("4. Отменить последний ход");
            System.out.println("5. Сдаться");
            System.out.print("Выберите: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод!");
                continue;
            }

            if (choice == 5) {
                System.out.println("\n" + attackerName + " сдался! Победитель: " + defenderName);
                gameOver = true;
                break;
            }

            if (choice == 4) {
                if (history.isEmpty()) {
                    System.out.println("Нет истории для отмены!");
                    continue;
                }
                // Кошмар: отмена через строки!
                String lastState = history.pop();
                String[] parts = lastState.split(";");
                // Кошмар: восстанавливаем из строк
                p1Health = Integer.parseInt(parts[0]);
                p2Health = Integer.parseInt(parts[1]);
                p1Blocking = Boolean.parseBoolean(parts[2]);
                p2Blocking = Boolean.parseBoolean(parts[3]);
                p1Cooldown = Integer.parseInt(parts[4]);
                p2Cooldown = Integer.parseInt(parts[5]);
                currentAttacker = (currentAttacker == 1) ? 2 : 1;
                turn--;
                System.out.println("Ход отменён!");
                continue;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Неверный выбор!");
                continue;
            }

            // Кошмар: сохранение состояние в виде строки
            String state = p1Health + ";" + p2Health + ";" +
                    p1Blocking + ";" + p2Blocking + ";" +
                    p1Cooldown + ";" + p2Cooldown;
            history.push(state);

            // Обработка действий - вся логика в одном месте
            if (choice == 1) { // Удар
                System.out.println("\n" + attackerName + " наносит удар!");
                int damage = (currentAttacker == 1) ? p1Damage : p2Damage;

                if (currentAttacker == 1) {
                    if (p2Blocking) {
                        damage = damage / 2;
                        System.out.println(defenderName + " блокирует! Урон уменьшен вдвое.");
                    }
                    p2Health -= damage;
                    if (p2Health < 0) p2Health = 0;
                    System.out.println(defenderName + " получает " + damage + " урона");
                } else {
                    if (p1Blocking) {
                        damage = damage / 2;
                        System.out.println(defenderName + " блокирует! Урон уменьшен вдвое.");
                    }
                    p1Health -= damage;
                    if (p1Health < 0) p1Health = 0;
                    System.out.println(defenderName + " получает " + damage + " урона");
                }

            } else if (choice == 2) { // Блок
                System.out.println("\n" + attackerName + " ставит блок!");
                if (currentAttacker == 1) {
                    p1Blocking = true;
                    p1Health += 10;
                    if (p1Health > p1MaxHealth) p1Health = p1MaxHealth;
                } else {
                    p2Blocking = true;
                    p2Health += 10;
                    if (p2Health > p2MaxHealth) p2Health = p2MaxHealth;
                }
                System.out.println(attackerName + " восстанавливает 10 здоровья");

            } else if (choice == 3) { // Специальная атака
                boolean canUse;
                if (currentAttacker == 1) {
                    canUse = (p1Cooldown == 0);
                } else {
                    canUse = (p2Cooldown == 0);
                }

                if (!canUse) {
                    System.out.println("\n" + attackerName + " не может использовать специальную атаку! Перезарядка!");
                    // Если не можем использовать - отменяем сохранение
                    history.pop();
                    continue;
                }

                System.out.println("\n" + attackerName + " использует специальную атаку!");
                int damage = (currentAttacker == 1) ? p1SpecialDamage : p2SpecialDamage;

                if (currentAttacker == 1) {
                    if (p2Blocking) {
                        damage = damage / 3;
                        System.out.println(defenderName + " блокирует! Урон уменьшен.");
                    }
                    p2Health -= damage;
                    if (p2Health < 0) p2Health = 0;
                    System.out.println(defenderName + " получает " + damage + " урона");
                    p1Cooldown = p1SpecialCooldown;
                } else {
                    if (p1Blocking) {
                        damage = damage / 3;
                        System.out.println(defenderName + " блокирует! Урон уменьшен.");
                    }
                    p1Health -= damage;
                    if (p1Health < 0) p1Health = 0;
                    System.out.println(defenderName + " получает " + damage + " урона");
                    p2Cooldown = p2SpecialCooldown;
                }
            }

            // Проверка на смерть
            if (p1Health <= 0 || p2Health <= 0) {
                System.out.println("\nИГРА ОКОНЧЕНА!");
                String winner = (p1Health <= 0) ? player2Name : player1Name;
                System.out.println("Победитель: " + winner + "!!!");
                gameOver = true;
                break;
            }

            // Сбрасывание блока и уменьшение перезарядки
            p1Blocking = false;
            p2Blocking = false;
            if (p1Cooldown > 0) p1Cooldown--;
            if (p2Cooldown > 0) p2Cooldown--;

            // Переключение хода
            currentAttacker = (currentAttacker == 1) ? 2 : 1;
            turn++;
        }
    }

    public void start() {
        processTurn();
        scanner.close();
    }

    public static void main(String[] args) {
        new BadGame().start();
    }
}
