package shell.commands;

/**
 * StopServer class: responsible for stop_server command
 */
public class StopServer extends Command {
    public StopServer(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Server stopped";
    }
}