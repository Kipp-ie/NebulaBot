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

public class Lyrics extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("lyrics")) {
            String songname = event.getOption("song").getAsString();
            String sURL = "https://api.popcat.xyz/lyrics?song=" + songname.replaceAll("\\s", "+");


            URL url = null;
            try {
                url = new URL(sURL);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            URLConnection request = null;
            try {
                request = url.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                request.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            JsonParser jp = new JsonParser();
            JsonElement root = null;
            try {
                root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JsonObject rootobj = root.getAsJsonObject();
            if (!(rootobj.get("error") == null)) {
                event.reply("Sorry! I couldn't find this song.").setEphemeral(true).queue();

            } else {
                String lyrics = String.format("%3.1024s", rootobj.get("lyrics"));
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Lyrics for " + rootobj.get("title").getAsString());
                embed.setAuthor(rootobj.get("artist").getAsString());
                embed.setImage(rootobj.get("image").getAsString());
                embed.addField("Lyrics", lyrics.replaceAll("\n", "\\s"), false);
                Dotenv dotenv = Dotenv.load();
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                String name = dotenv.get("BOT_NAME");
                embed.setFooter(name);
                event.replyEmbeds(embed.build()).queue();
            }
        }
    }
}
