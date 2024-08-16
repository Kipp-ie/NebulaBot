package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class Avatar extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("avatar")) {
            Dotenv dotenv = Dotenv.load();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Avatar of " + event.getOption("user").getAsUser().getName());
            embed.setImage(Objects.requireNonNull(event.getOption("user")).getAsUser().getAvatarUrl());
            String color = dotenv.get("COLOR");
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setColor(Color.decode(color));
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
