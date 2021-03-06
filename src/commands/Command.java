package commands;

import helperCore.PermissionLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public interface Command {

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);
    String help();

    default ArrayList<String> blockedServerIDs() {
        return new ArrayList<>();
    }
    boolean isPrivate();
    String Def(String prefix, Guild g);
    default PermissionLevel PermLevel() {
        return PermissionLevel.EVERYONE;
    }


}