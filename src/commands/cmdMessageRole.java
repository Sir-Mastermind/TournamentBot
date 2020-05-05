package commands;

import listeners.commandListener;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.STATIC;

import java.util.List;
import java.util.Objects;

public class cmdMessageRole implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String prefix = commandListener.getPrefix(event.getGuild());
        if (!event.getAuthor().getId().equalsIgnoreCase(STATIC.OWNERID)) return;
        if (args.length<4) {
            event.getTextChannel().sendMessage("Usage: `"+prefix+"messagetorole [add/rem] [TextChannelID] [MessageID] [RoleID] [ReactEmoji]`").queue();
            return;
        }
        Message msg = Objects.requireNonNull(event.getGuild().getTextChannelById(args[1])).getHistoryAround(args[2],5).complete().getMessageById(args[2]);
        if (msg==null) {
            event.getTextChannel().sendMessage("Nachricht nicht gefunden!").queue();
            return;
        }
        List<MessageReaction> msrs =msg.getReactions();
        MessageReaction mr = null;
        for (MessageReaction mer:msrs) {
            if (mer.getReactionEmote().getEmoji().equalsIgnoreCase(args[4])) mr = mer;
        }
        if (mr==null) {
            event.getTextChannel().sendMessage("Reaction nicht gefunden!").queue();
            return;
        }
        List<User> usrs =mr.retrieveUsers().complete();
        Role r = event.getGuild().getRoleById(args[3]);
        if (r==null) {
            event.getTextChannel().sendMessage("Rolle nicht gefunden!").queue();
            return;
        }
        for (User u:usrs) {
            if (event.getGuild().isMember(u)) {
                Member m =event.getGuild().getMember(u);
                assert m !=null;
                if (args[0].equalsIgnoreCase("add")) {
                    event.getGuild().addRoleToMember(m,r).queue();
                }
                if (args[0].equalsIgnoreCase("rem")) {
                    event.getGuild().removeRoleFromMember(m,r).queue();
                }
            }
        }
        event.getTextChannel().sendMessage("Erfolgreich "+usrs.size()+" Nutzer hinzugefügt").queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public boolean isPrivate() {
        return true;
    }

    @Override
    public String Def(String prefix) {
        return "Administartionscommand";
    }
}