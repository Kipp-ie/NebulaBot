package dev.kippie.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Cat extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("cat")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Here's your random cat picture!");
            embed.setImage("https://cataas.com/cat");
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setColor(Color.decode(color));
            event.replyEmbeds(embed.build()).queue();

        }
    }
}
