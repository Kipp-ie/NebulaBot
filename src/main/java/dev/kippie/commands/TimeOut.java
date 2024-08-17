package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class TimeOut extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timeout")) {
            if (event.getMember().hasPermission(Permission.MODERATE_MEMBERS)) {

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("You have received a timeout in " + event.getGuild().getName());
                embed.addField("Reason", event.getOption("reason").getAsString(), false);
                embed.addField("Duration", "You have received a timeout for " + event.getOption("duration").getAsString() + " hour(s).", false);
                Dotenv dotenv = Dotenv.load();
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                String name = dotenv.get("BOT_NAME");
                embed.setFooter(name);
                event.getOption("user").getAsUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed.build())).queue(message -> {
                    event.reply(event.getOption("user").getAsUser().getName() + " has received a timeout in " + event.getGuild().getName()).setEphemeral(true).queue();
                    event.getOption("user").getAsMember().timeoutFor(event.getOption("duration").getAsInt(), TimeUnit.HOURS).reason(event.getOption("reason").getAsString()).queue();
                });
            } else {
                event.reply("You don't have permission for this!").setEphemeral(true).queue();
            }
        }
    }
}
