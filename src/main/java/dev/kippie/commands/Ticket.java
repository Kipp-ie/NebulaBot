package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
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
            if (event.getGuild().getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {
                event.getUser().openPrivateChannel().map(channel -> {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Open a ticket.");
                    embed.setDescription("Please select one of the ticket subjects below.");
                    channel.sendMessageEmbeds(embed.build())
                            .addActionRow(
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("report", "Report").withEmoji(Emoji.fromUnicode("U+26A0")),
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("questions", "Questions").withEmoji(Emoji.fromUnicode("U+2753")),
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("other", "Other").withEmoji(Emoji.fromUnicode("U+1F4AC"))
                            ).queue();
                    event.reply("Please check your personal dm's to create a ticket.").setEphemeral(true).queue();
                    return null;
                }).queue();

            } else {
                event.reply("You already have a ticket open! <#" + event.getGuild().getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">" ).setEphemeral(true).queue();
            }






        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("close")) {

        } else if (event.getComponentId().equals("report")) {
            Guild guild = event.getJDA().getGuildById(dotenv.get("GUILD_ID"));
            if (guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {


                guild.createTextChannel("ticket-" + event.getUser().getName()).queue(textChannel -> {
                    event.reply("Your ticket has been made! <#" + textChannel.getId() + ">").setEphemeral(true).queue();
                });


            } else {
                event.reply("You already have a ticket open! <#" + event.getGuild().getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">" ).setEphemeral(true).queue();
            }

        }
    }
}
