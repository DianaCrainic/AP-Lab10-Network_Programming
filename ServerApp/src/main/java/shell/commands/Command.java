package shell.commands;

/**
 * Command class: abstract class
 */
public abstract class Command {
    private final String command;
    private String arguments;

    public Command(String command) {
        this.command = command;
    }

    public Command(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public String getArguments() {
        return arguments;
    }

    public abstract String execute(Object... arguments);

    @Override
    public String toString() {
        return command + " " + arguments;
    }
}
