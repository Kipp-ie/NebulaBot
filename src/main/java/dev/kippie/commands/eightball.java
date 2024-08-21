package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;

public class eightball extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("eightball")) {
            ArrayList<String> response = new ArrayList<String>();
            response.add("Yes");
            response.add("No");
            response.add("Probably not");
            response.add("Probably");
            response.add("You should ask ChatGPT");
            response.add("Never");
            response.add("Always");
            response.add("Totally");
            response.add("Totally not");
            response.add("Absolutely");
            response.add("Absolutely not");
            response.add("You should ask this on Reddit tbh, https://www.reddit.com/r/AskReddit/");
            response.add("You should never gonna give yourself up!");
            response.add("Ask this to the president of Nepal");
            response.add("NONONONOONONONONO");
            response.add("You should ask Gemini");
            response.add("Ask " + event.getGuild().getMembers().get((int) (Math.random() * event.getGuild().getMemberCount())).getUser().getName());

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("8ball");
            embed.addField("Question", event.getOption("question").getAsString(), false);
            embed.addField("Response", response.get((int) (Math.random() * 17 + 1)), false);
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            event.replyEmbeds(embed.build()).queue();

        }
    }
}
