package behavioral.command.after.command;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;

public class SpecialAttackCommand extends BattleCommand{
    private boolean actionPerformed;

    public SpecialAttackCommand(Character _attacker, Character _defender, GameState _state) {
        super(_attacker, _defender, _state);
        this.actionPerformed = false;
    }

    @Override
    public void execute() {
        if (!attacker.canUseSpecial()) {
            System.out.println("\n " + attacker.getName() + " не может использовать специальную атаку. Идёт перезарядка");
            return;
        }

        state.saveState();
        actionPerformed = true;

        System.out.println("\n " + attacker.getName() + " использует специальную атаку");
        int actualDamage = defender.takeSpecialDamage(attacker.getSpecialDamage());
        System.out.println(defender.getName() + " получает " + actualDamage + " урона");
        attacker.useSpecial();
        System.out.println(defender);
        System.out.println(attacker.getName() + " : специальная атака на перезарядке");
    }

    @Override
    public void undo() {
        if (actionPerformed) {
            super.undo();
        } else {
            System.out.println("Нечего отменять — специальная атака не была выполнена");
        }
    }
}