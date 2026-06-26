package behavioral.command.after.model;

import java.util.Stack;

public class GameState {
    private Character player1;
    private Character player2;

    // Внутренний класс для хранения снимка состояния
    private static class Snapshot {
        int player1_health;
        int player2_health;
        boolean player1_isBlocking;
        boolean player2_isBlocking;
        int player1_cooldown;
        int player2_cooldown;

        Snapshot(int h1, int h2, boolean b1, boolean b2, int c1, int c2) {
            this.player1_health = h1;
            this.player2_health = h2;
            this.player1_isBlocking = b1;
            this.player2_isBlocking = b2;
            this.player1_cooldown = c1;
            this.player2_cooldown = c2;
        }
    }

    private Stack<Snapshot> history;

    public GameState(Character _player1, Character _player2) {
        this.player1 = _player1;
        this.player2 = _player2;
        this.history = new Stack<>();
        saveState();
    }

    public void saveState() {
        history.push(new Snapshot(
                player1.getHealth(),
                player2.getHealth(),
                player1.isBlocking(),
                player2.isBlocking(),
                player1.getCurrentCooldown(),
                player2.getCurrentCooldown()
        ));
    }

    public void restoreState() {
        if (history.isEmpty()) {
            System.out.println("Нет сохранённых состояний для восстановления!");
            return;
        }
        Snapshot snap = history.pop();
        player1.setHealth(snap.player1_health);
        player2.setHealth(snap.player2_health);
        player1.setBlocking(snap.player1_isBlocking);
        player2.setBlocking(snap.player2_isBlocking);
        player1.setCurrentCooldown(snap.player1_cooldown);
        player2.setCurrentCooldown(snap.player2_cooldown);
    }
}