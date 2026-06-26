package behavioral.command.after.game;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;
import behavioral.command.after.command.*;

import java.util.*;

public class Game {
    private Character player1;
    private Character player2;
    private GameState gameState;
    private Stack<Command> historyCommand;
    private Scanner scanner;
    private Character currentAttacker;
    private Character currentDefender;
    private int cntTurn;
    private boolean gameOver;

    public Game() {
        scanner = new Scanner(System.in);
        historyCommand = new Stack<>();
        cntTurn = 0;
        gameOver = false;
        initCharacters();
        gameState = new GameState(player1, player2);
    }

    private void initCharacters() {
        System.out.println("СМЕРТЕЛЬНАЯ БИТВА \n");
        System.out.println("Имя 1-го бойца: ");
        String name1 = scanner.nextLine();
        System.out.println("Имя 2-го бойца: ");
        String name2 = scanner.nextLine();

        player1 = new Character(name1, 100);
        player2 = new Character(name2, 100);

        System.out.println("\n Начало боя!");
    }

    private void switchTurn() {
        if (currentAttacker == player1) {
            currentAttacker = player2;
            currentDefender = player1;
        }
        else {
            currentAttacker = player1;
            currentDefender = player2;
        }
    }

    private void displayStatus() {
        System.out.println("\nХод №" + cntTurn);
        System.out.println(player1);
        System.out.println(player2);
        System.out.println("\nСейчас атакует: " + currentAttacker.getName());
        System.out.println("В защите: " + currentDefender.getName());
    }

    private void displayMenu() {
        System.out.println("\n Действия:");
        System.out.println("1. Удар (20 урона)");
        System.out.println("2. Блок (уменьшает полученный урон в 2 раза)");
        System.out.println("3. Специальная атака (35 урона)");
        System.out.println("4. Отменить последний ход");
        System.out.println("5. Сдаться и закончить игру");
        System.out.println("Выберете действие: ");
    }

    private Command getCommand(int choice) {
        switch (choice) {
            case 1:
                return new StrikeCommand(currentAttacker, currentDefender, gameState);
            case 2:
                return new BlockCommand(currentAttacker, currentDefender, gameState);
            case 3:
                return new SpecialAttackCommand(currentAttacker, currentDefender, gameState);
            default:
                return null;
        }
    }

    private void resetBlockStatus() {
        player1.resetBlock();
        player2.resetBlock();
    }

    private void processTurn() {
        boolean validFlag = false;
        while (!validFlag) {
            displayStatus();
            displayMenu();

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод! Повторите снова!");
                continue;
            }

            if (choice == 5) {
                System.out.println("\n" + currentAttacker.getName() + " сдался. Победитель: " + currentDefender.getName());
                gameOver = true;
                return;
            }
            if (choice == 4) {
                if (historyCommand.isEmpty()) {
                    System.out.println("\n История пуста! Отменять нечего!");
                    continue;
                }

                Command lastCommand = historyCommand.pop();
                lastCommand.undo();

                switchTurn();
                cntTurn--;

                System.out.println("\n Статус после отмены хода:");
                System.out.println(player1);
                System.out.println(player2);
                continue;
            }
            if (choice < 1 || choice > 5) {
                System.out.println("Неверный выбор! Выберете действие 1-5!");
                continue;
            }

            Command command = getCommand(choice);
            if (command != null) {
                command.execute();
                historyCommand.push(command);

                if (!currentDefender.isAlive()) {
                    System.out.println("\n ИГРА ОКОНЧЕНА!");
                    System.out.println("Победитель: " + currentAttacker.getName() + "!!!");
                    gameOver = true;
                    return;
                }

                cntTurn++;
                switchTurn();
                validFlag = true;
            }
        }

        player1.decreaseCooldown();
        player2.decreaseCooldown();
        resetBlockStatus();
    }

    public void start() {
        currentAttacker = player1;
        currentDefender = player2;

        while (!gameOver) { processTurn(); }
        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}