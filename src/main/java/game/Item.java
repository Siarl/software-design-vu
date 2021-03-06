package game;


import java.util.ArrayList;
import java.util.List;

public class Item implements Interactable {

    private String name;
    private String description;
    private int actionId;
    private transient Actions actions;

    public Item(String name, String description, int actionId) {
        this.name = name;
        this.description = description;
        this.actionId = actionId;
    }

    @Override
    public boolean handleCommand(Command command, Callback callback) {
        if (actions == null) {
            actions = command.getGame().getActionsMap().get(actionId);
        }

        if (command.hasReceiver() && command.getReceiver().equals(name)) {
            if (command.getAction().equals("inspect")) {
                // TODO: 04-03-2020 replace with Action.listCommands()
                StringBuilder message = new StringBuilder("inspect: item, name = ");
                message.append(name).append("\n");
                message.append(description).append("\n\n");
                message.append("commands:").append("\n");

                List<String> commands = listHandledCommands(command.getGame());
                commands.forEach(s -> message.append("\t").append(s).append("\n"));

                callback.onMessage(message.toString());

            } else if (actions.hasCommand(command.getAction())) {
                // TODO: 04-03-2020 replace with Action.onCommand()
                Effects effects = actions.getEffects(command.getAction());
                if (effects != null) {

                    effects.apply(command.getGame(), callback);
                    callback.onMessage("Effect applied... " + effects.toString());
                }
            } else {
                callback.onMessage("This command does not exist for this item.");
            }

            return true;
        }

        return false;
    }

    @Override
    public List<String> listHandledCommands(Game game) {
        List<String> result = new ArrayList<>();
        result.add("inspect " + name);
        actions.listHandledCommands(game).forEach(s -> result.add(s + " " + name));
        return result;
    }

    /*
    Getters & Setters
     */

    public String getName() {
        return name;
    }

    public Actions getActions() {
        return actions;
    }

    public String getDescription() {
        return description;
    }
}
