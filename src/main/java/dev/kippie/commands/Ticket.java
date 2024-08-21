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
import java.util.Objects;

public class Ticket extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticket")) {
            if (Objects.requireNonNull(event.getGuild()).getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {
                event.getUser().openPrivateChannel().map(channel -> {
                    EmbedBuilder embed = new EmbedBuilder();
                    Dotenv dotenv = Dotenv.load();
                    embed.setTitle("Open a ticket.");
                    embed.setDescription("Please select one of the ticket subjects below.");
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    channel.sendMessageEmbeds(embed.build())
                            .addActionRow(
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("report", "Report").withEmoji(Emoji.fromUnicode("U+26A0")),
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("questions", "Questions").withEmoji(Emoji.fromUnicode("U+2753")),
                                    net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("other", "Other").withEmoji(Emoji.fromUnicode("U+1F4AC"))
                            ).queue();
                    event.reply("Please check your personal messages to create a ticket.").setEphemeral(true).queue();
                    return null;
                }).queue();

            } else {
                event.reply("You already have a ticket open! <#" + event.getGuild().getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">").setEphemeral(true).queue();
            }


        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("close")) {
            if (event.getUser().equals(event.getJDA().getUserById(Objects.requireNonNull(event.getChannel().asTextChannel().getTopic()))) || Objects.requireNonNull(event.getMember()).getRoles().contains(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("SUPPORT_ROLE")))) {
                event.getChannel().delete().queue();
                Objects.requireNonNull(event.getJDA().getUserById(Objects.requireNonNull(event.getChannel().asTextChannel().getTopic()))).openPrivateChannel().map(channel -> {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Your ticket has been closed");
                    embed.setDescription("Feel free to open a new ticket if you need assistance!");
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    return null;
                }).queue();
            } else {
                event.reply("You are not the owner of this ticket or part of the support team.").setEphemeral(true).queue();
            }

        } else if (event.getComponentId().equals("report")) {
            Guild guild = event.getJDA().getGuildById(dotenv.get("GUILD_ID"));
            assert guild != null;
            if (guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {


                Objects.requireNonNull(guild.getCategoryById(dotenv.get("TICKET_CATEGORY"))).createTextChannel("ticket-" + event.getUser().getName()).queue(textChannel -> {
                    textChannel.upsertPermissionOverride(Objects.requireNonNull(guild.getMemberById(event.getUser().getId()))).grant(Permission.VIEW_CHANNEL).queue();
                    event.reply("Your ticket has been made! <#" + textChannel.getId() + ">").setEphemeral(true).queue();
                    textChannel.getManager().setTopic(event.getUser().getId()).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Ticket " + event.getUser().getName());
                    embed.setDescription("Welcome to your Ticket, staff will be with you soon. Feel free to explain what the reason of the ticket is now!");
                    embed.addField("Reason:", "Report", false);
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);

                    textChannel.sendMessageEmbeds(embed.build()).addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("close", "Close").withEmoji(Emoji.fromUnicode("U+26A0"))
                    ).queue();
                    textChannel.sendMessage("<@&" + dotenv.get("SUPPORT_ROLE") + ">, <@" + event.getUser().getId() + ">").queue();
                });


            } else {
                event.reply("You already have a ticket open! <#" + guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">").setEphemeral(true).queue();
            }

        } else if (event.getComponentId().equals("questions")) {
            Guild guild = event.getJDA().getGuildById(dotenv.get("GUILD_ID"));
            assert guild != null;
            if (guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {


                Objects.requireNonNull(guild.getCategoryById(dotenv.get("TICKET_CATEGORY"))).createTextChannel("ticket-" + event.getUser().getName()).queue(textChannel -> {
                    textChannel.upsertPermissionOverride(Objects.requireNonNull(guild.getMemberById(event.getUser().getId()))).grant(Permission.VIEW_CHANNEL).queue();
                    event.reply("Your ticket has been made! <#" + textChannel.getId() + ">").setEphemeral(true).queue();
                    textChannel.getManager().setTopic(event.getUser().getId()).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Ticket " + event.getUser().getName());
                    embed.setDescription("Welcome to your Ticket, staff will be with you soon. Feel free to ask your question!");
                    embed.addField("Reason:", "Questions", false);
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);

                    textChannel.sendMessageEmbeds(embed.build()).addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("close", "Close").withEmoji(Emoji.fromUnicode("U+26A0"))
                    ).queue();
                    textChannel.sendMessage("<@&" + dotenv.get("SUPPORT_ROLE") + ">, <@" + event.getUser().getId() + ">").queue();
                });


            } else {
                event.reply("You already have a ticket open! <#" + guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">").setEphemeral(true).queue();
            }
        } else if (event.getComponentId().equals("other")) {
            Guild guild = event.getJDA().getGuildById(dotenv.get("GUILD_ID"));
            assert guild != null;
            if (guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).isEmpty()) {


                Objects.requireNonNull(guild.getCategoryById(dotenv.get("TICKET_CATEGORY"))).createTextChannel("ticket-" + event.getUser().getName()).queue(textChannel -> {
                    textChannel.upsertPermissionOverride(Objects.requireNonNull(guild.getMemberById(event.getUser().getId()))).grant(Permission.VIEW_CHANNEL).queue();
                    event.reply("Your ticket has been made! <#" + textChannel.getId() + ">").setEphemeral(true).queue();
                    textChannel.getManager().setTopic(event.getUser().getId()).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Ticket " + event.getUser().getName());
                    embed.setDescription("Welcome to your Ticket, staff will be with you soon. Feel free to explain what the reason of the ticket is now!");
                    embed.addField("Reason:", "Other", false);
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);

                    textChannel.sendMessageEmbeds(embed.build()).addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("close", "Close").withEmoji(Emoji.fromUnicode("U+26A0"))
                    ).queue();
                    textChannel.sendMessage("<@&" + dotenv.get("SUPPORT_ROLE") + ">, <@" + event.getUser().getId() + ">").queue();
                });


            } else {
                event.reply("You already have a ticket open! <#" + guild.getTextChannelsByName("ticket-" + event.getUser().getName(), true).getFirst().getId() + ">").setEphemeral(true).queue();
            }

        }
    }
}