package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Actions implements Interactable {

    private int id;
    private Map<String, Effects> commandEffectsMap;

    public Actions(int id, Map<String, Effects> commandEffectsMap) {
        this.id = id;
        this.commandEffectsMap = commandEffectsMap;
    }

    @Override
    public boolean handleCommand(Command command, Callback callback) {
        if (hasCommand(command.getAction())) {
            Effects effects = getEffects(command.getAction());
            if (effects != null) {
                effects.apply(command.getGame(), callback);

                callback.onMessage("Effect applied... " + effects.toString());
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> listHandledCommands(Game game) {
        List<String> result = new ArrayList<>();
        Set<String> commands = getCommandSet();
        if (commands != null) {
            result.addAll(getCommandSet());
        }
        return result;
    }

    /*
    Getters - just wrappers for Map methods
     */

    public int getId() {
        return id;
    }

    public Effects getEffects(String command) {
        if (commandEffectsMap == null) return null;
        return commandEffectsMap.get(command);
    }

    public Set<String> getCommandSet() {
        if (commandEffectsMap == null) return null;
        return commandEffectsMap.keySet();
    }

    public boolean hasCommand(String command) {
        return commandEffectsMap != null && commandEffectsMap.containsKey(command);
    }
}
