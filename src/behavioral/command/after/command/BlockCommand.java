package behavioral.command.after.command;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;

public class BlockCommand extends BattleCommand {
    public BlockCommand(Character _attacker, Character _defender, GameState _state) {
        super(_attacker, _defender, _state);
    }

    @Override
    public void execute() {
        state.saveState();
        System.out.println("\n " + attacker.getName() + " ставит блок и будет блокировать следующие атаки");
        attacker.setBlocking(true);
        attacker.heal(10);
    }
}