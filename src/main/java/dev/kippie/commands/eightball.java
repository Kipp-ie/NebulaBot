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
            response.add("Maybe");
            response.add("I guess we'll never know");
            response.add("https://open.spotify.com/track/2ygeBLTP9uu3OW3VTulD8N?si=cc18070000dc4225");
            response.add("Why do you let a eightball answer life's hardest question?");
            response.add("ERROR: No response found for this stupid question");
            response.add("You should ask yourself tbh");
            response.add("What do you think? OFCOURSE NOT???");
            response.add("I don't even know");
            response.add("Ask your parents, they should know.");
            response.add("You should ask your friends! (If you even have any");
            response.add("Sorry. i can't give an answer to this question.");
            response.add("Nah");
            response.add("yea");



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
