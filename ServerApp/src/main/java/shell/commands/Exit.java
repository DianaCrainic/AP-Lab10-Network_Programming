package shell.commands;

/**
 * Exit class: responsible for exit command
 */
public class Exit extends Command {
    public Exit(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "You've left the game";
    }
}