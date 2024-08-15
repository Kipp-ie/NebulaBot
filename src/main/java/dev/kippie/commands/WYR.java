package dev.kippie.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WYR extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("wyr")) {
            String sURL = "https://api.popcat.xyz/wyr";


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
            String option1 = rootobj.get("ops1").getAsString();
            String option2 = rootobj.get("ops2").getAsString();
            event.reply("WYR posted!").setEphemeral(true).queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("WYD - Would you Rather");
            embed.addField("Option 1", option1, false);
            embed.addField("Option 2", option2, false);
            embed.setAuthor(event.getUser().getName());
            event.getChannel().sendMessageEmbeds(embed.build()).queue(message -> {
                message.addReaction(Emoji.fromUnicode("1\uFE0F⃣")).queue();
                message.addReaction(Emoji.fromUnicode("2\uFE0F⃣")).queue();
            });
        }
    }
}
