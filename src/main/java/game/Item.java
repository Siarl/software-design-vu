package game;


import java.util.List;

public class Item implements Interactable {

    private String name;
    private String description;
    private Actions actions;
    private int actionId;

    @Override
    public boolean onCommand(Command command, Callback callback) {
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

                List<String> commands = listCommands(command.getGame());
                commands.forEach(s -> message.append("\t").append(s).append("\n"));

                callback.onMessage(message.toString());

            } else if (actions.hasCommand(command.getAction())) {
                // TODO: 04-03-2020 replace with Action.onCommand()
                Effects effect = actions.getEffects(command.getAction());
                if (effect != null) {

                    effect.apply(command.getGame());
                    callback.onMessage("Effect applied... " + effect.toString());
                }
            } else {
                callback.onMessage("This command does not exist for this item.");
            }

            return true;
        }

        return false;
    }

    @Override
    public List<String> listCommands(Game game, List<String> addToThisList) {
        addToThisList.add("inspect " + name);
        actions.listCommands(game).forEach(s -> addToThisList.add(s + " " + name));
        return addToThisList;
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
