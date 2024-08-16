package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class SadCat extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("sadcat")) {
            String text1 = event.getOption("text").getAsString();
            String text = text1.replaceAll("\\s", "+");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Here is your SadCat meme!");
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setImage("https://api.popcat.xyz/sadcat?text=" + text);
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
