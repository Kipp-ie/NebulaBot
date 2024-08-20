package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;

public class Suggestion extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("suggestion")) {
            Dotenv dotenv = Dotenv.load();
            String suggestionid = dotenv.get("SUGGESTIONS_CHANNEL");

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("New suggestion from " + event.getUser().getName() + "!");
            embed.addField("Suggestion", event.getOption("suggestion").getAsString(), false);
            embed.setDescription("Reply with " + Emoji.fromUnicode("U+1F4A1").getFormatted() + " if you agree with this suggestion!");
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            embed.setFooter(event.getUser().getId());
            event.getGuild().getTextChannelById(suggestionid).sendMessageEmbeds(embed.build())
                    .addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("suggestiondeny", "Deny"),
                            net.dv8tion.jda.api.interactions.components.buttons.Button.success("suggestionaccept", "Accept")
                    )

                    .queue(message -> {
                message.addReaction(Emoji.fromUnicode("U+1F4A1")).queue();
                event.reply("Suggestion has been made in <#" + suggestionid + ">").setEphemeral(true).queue();
            });



            }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("suggestiondeny")) {

            if (event.getMember().getRoles().contains(event.getGuild().getRoleById(dotenv.get("SUGGESTIONS_ROLE")))) {
                String suggestion = event.getMessage().getEmbeds().getFirst().getFields().getFirst().getValue();
                User user = event.getJDA().getUserById(Objects.requireNonNull(Objects.requireNonNull(event.getMessage().getEmbeds().getFirst().getFooter()).getText()));
                EmbedBuilder embed = new EmbedBuilder();
                embed.addField("Suggestion has been denied.", suggestion, false);
                embed.setColor(Color.RED);
                embed.setTitle("The suggestion from " + user.getName() + " has been denied");

                event.editMessageEmbeds(embed.build()).setComponents()
                        .queue(message -> {
                            EmbedBuilder embed2 = new EmbedBuilder();
                            embed2.setTitle("Your suggestion has been denied.");
                            embed2.addField("Suggestion", suggestion, false);
                            embed2.addField("Have questions?", "Dm the reviewer of your suggestion @" + event.getUser().getName(), false);
                            embed2.setColor(Color.RED);
                            user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();


                        });
            }
        } else if (event.getComponentId().equals("suggestionaccept")) {
                if (event.getMember().getRoles().contains(event.getGuild().getRoleById(dotenv.get("SUGGESTIONS_ROLE")))) {

                    String suggestion = event.getMessage().getEmbeds().getFirst().getFields().getFirst().getValue();
                    User user = event.getJDA().getUserById(Objects.requireNonNull(Objects.requireNonNull(event.getMessage().getEmbeds().getFirst().getFooter()).getText()));
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.addField("Suggestion has been accepted!", suggestion, false);
                    embed.setColor(Color.GREEN);
                    embed.setTitle("The suggestion from " + user.getName() + " has been accepted!");

                    event.editMessageEmbeds(embed.build()).setComponents()
                            .queue(message -> {
                                EmbedBuilder embed2 = new EmbedBuilder();
                                embed2.setTitle("Your suggestion has been accepted.");
                                embed2.addField("Suggestion", suggestion, false);
                                embed2.setColor(Color.GREEN);
                                user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();


                });



            }}
        }
    }
