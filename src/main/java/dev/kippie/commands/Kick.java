package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Kick extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("kick")) {
            if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("You have been kicked from " + event.getGuild().getName());
                embed.addField("Reason", event.getOption("reason").getAsString(), false);
                Dotenv dotenv = Dotenv.load();
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                String name = dotenv.get("BOT_NAME");
                embed.setFooter(name);
                event.getOption("user").getAsUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed.build())).queue(message -> {
                    event.reply(event.getOption("user").getAsUser().getName() + " has been kicked from " + event.getGuild().getName()).setEphemeral(true).queue();
                    event.getOption("user").getAsMember().kick().queue();
                });
            } else {
                event.reply("You don't have permission for this!").setEphemeral(true).queue();
            }
        }
    }
}
