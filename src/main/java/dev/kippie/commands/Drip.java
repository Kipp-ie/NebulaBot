package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Drip extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("drip")) {
            EmbedBuilder embed = new EmbedBuilder();
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setTitle("You drippified " + event.getOption("user").getAsUser().getName());
            embed.setImage("https://api.popcat.xyz/drip?image=" + event.getOption("user").getAsUser().getAvatarUrl());
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
