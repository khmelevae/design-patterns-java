package behavioral.command.after.command;

import behavioral.command.after.model.Character;
import behavioral.command.after.model.GameState;

public interface Command {
    void execute();
    void undo();
}