package behavioral.command.after.command;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;

abstract class BattleCommand implements Command {
    protected Character attacker;
    protected Character defender;
    protected GameState state;

    public BattleCommand (Character _attacker, Character _defender, GameState _state) {
        this.attacker = _attacker;
        this.defender = _defender;
        this.state = _state;
    }

    @Override
    public void undo() {
        state.restoreState();
        System.out.println("Ход отменён");
    }
}