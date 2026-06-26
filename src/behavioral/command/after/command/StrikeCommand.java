package behavioral.command.after.command;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;

public class StrikeCommand extends BattleCommand {
    public StrikeCommand(Character _attacker, Character _defender, GameState _state) {
        super(_attacker, _defender, _state); // конструктор родителя
    }

    @Override
    public void execute() {
        state.saveState();

        System.out.println("\n " + attacker.getName() + " наносит удар");
        int actualDamage = defender.takeDamage(attacker.getDamage());
        System.out.println(defender.getName() + " получает " + actualDamage + " урона");
        System.out.println(defender);
    }
}