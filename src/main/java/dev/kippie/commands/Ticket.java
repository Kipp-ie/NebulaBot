package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Ticket extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticket")) {
            Dotenv dotenv = Dotenv.load();
            String ticket_category = dotenv.get("TICKET_CATEGORY");
            String ticketname = "ticket-" + event.getUser().getName();
            List textchannels = event.getGuild().getTextChannelsByName(ticketname, true);
            if (!textchannels.isEmpty()) {
                event.reply("You already have a ticket open!").setEphemeral(true).queue();

            } else {
                event.getGuild().getCategoryById(ticket_category).createTextChannel(ticketname).queue(channel -> {
                    channel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL).queue();
                    event.reply("Ticket has been created! <#" + channel.getId() + ">").queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Ticket " + event.getUser().getName());
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    embed.addField("Closing this ticket:", "If you want to close this ticket, use /close!", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                });




            }
        }
    }
}
