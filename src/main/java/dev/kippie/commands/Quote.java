package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Quote extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("quote")) {
            User user = event.getOption("user").getAsUser();
            String quote = event.getOption("quote").getAsString().replaceAll("\\s", "+");

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Here's your quote!");
            embed.setImage("https://api.popcat.xyz/quote?image=" + user.getAvatarUrl() + "&text=" + quote + "&font=Poppins-Bold&name=" + user.getName());
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
