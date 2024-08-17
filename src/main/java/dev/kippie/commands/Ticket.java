package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
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
            List tickets = event.getGuild().getTextChannelsByName(ticketname, true);
            if (!tickets.isEmpty()) {
                event.reply("You already have a ticket open!").setEphemeral(true).queue();

            } else {
                event.getGuild().getCategoryById(ticket_category).createTextChannel(ticketname).queue(channel -> {
                    channel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL).queue();
                    event.reply("Ticket has been created! <#" + channel.getId() + ">").setEphemeral(true).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Ticket " + event.getUser().getName());
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    embed.addField("Closing this ticket:", "Click on the red button! Be warned, when the ticket is deleted it's gone forever!", false);
                    embed.addField("Adding another user to this ticket:", "If you wish to add someone to this ticket use /add.", false);
                    channel.sendMessageEmbeds(embed.build())
                            .addActionRow(
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("close", "Close").withEmoji(Emoji.fromUnicode("U+274C"))
                            )

                            .queue();
                });




            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("close")) {
            event.getChannel().delete().queue();
        }
    }
}
