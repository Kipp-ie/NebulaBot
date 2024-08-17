package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Add extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("add")) {
            Dotenv dotenv = Dotenv.load();
            String ticket_category = dotenv.get("TICKET_CATEGORY");
            List ticketlist = event.getGuild().getCategoryById(ticket_category).getChannels();
            if (ticketlist.contains(event.getChannel())) {
                event.getChannel().asTextChannel().upsertPermissionOverride(event.getOption("user").getAsMember()).grant(Permission.VIEW_CHANNEL).queue();
                event.reply(event.getOption("user").getAsUser().getName() + " has been added to this ticket.").queue();
            }
        }
    }
}
