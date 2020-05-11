package shell.commands;

/**
 * ShowCommands class: responsible for show_commands command
 */
public class ShowCommands extends Command {

    public ShowCommands(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Commands: " + arguments[0];
    }
}