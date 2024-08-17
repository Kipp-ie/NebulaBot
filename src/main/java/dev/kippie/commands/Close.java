package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Close extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("close")) {
            Dotenv dotenv = Dotenv.load();
            String ticket_category = dotenv.get("TICKET_CATEGORY");
            String closed_ticket_category = dotenv.get("CLOSED_TICKET_CATEGORY");
            List ticketchannels = event.getGuild().getCategoryById(ticket_category).getChannels();
            if (ticketchannels.contains(event.getChannel())) {
                event.reply("Ticket has been closed").queue();
                event.getChannel().asTextChannel().getManager().setParent(event.getGuild().getCategoryById(closed_ticket_category)).queue();
            } else {
                event.reply("You can only close tickets when you execute this command in a ticket!").setEphemeral(true).queue();

            }
        }
    }
}
