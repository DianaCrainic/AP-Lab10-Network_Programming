package shell.commands;

import game.Player;

/**
 * SetName class: responsible for set_name command
 */
public class SetName extends Command {
    public SetName(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Please specify your name: set_name name";
        }
        Player player = (Player) arguments[0];
        player.setName((String) arguments[1]);
        return "Your name is: " + arguments[1];
    }
}