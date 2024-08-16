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
import java.io.Serial;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Dog extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("dog")) {
            String sURL = "https://dog.ceo/api/breeds/image/random";


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
            String image_url = rootobj.get("message").getAsString();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Here's your random dog picture!");
            embed.setImage(image_url);
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setColor(Color.decode(color));
            event.replyEmbeds(embed.build()).queue();


        }
    }
}
