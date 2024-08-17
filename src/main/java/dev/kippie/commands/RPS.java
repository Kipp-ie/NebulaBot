package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class RPS extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("rps")) {
            int random = (int )(Math.random() * 3 + 1);
            String choice = event.getOption("choice").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Rock, paper, scissors");
            Dotenv dotenv = Dotenv.load();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);

            if (random == 1 && choice.equals("rock")) {
                embed.addField("Draw!", "\uD83E\uDEA8 VS \uD83E\uDEA8 = a draw!", false);
            }
            if (random == 2 && choice.equals("paper")) {
                embed.addField("Draw!", "\uD83D\uDCDC VS \uD83D\uDCDC = a draw!", false);
            }
            if (random == 3 && choice.equals("scissors")) {
                embed.addField("Draw!", "✂ VS ✂ = a draw!", false);
            }
            if (random == 1 && choice.equals("paper")) {
                embed.addField("You win!", "\uD83D\uDCDC VS \uD83E\uDEA8 = you win!", false);
            }
            if (random == 1 && choice.equals("scissors")) {
                embed.addField("You lose!", "✂ vs \uD83E\uDEA8 = you lose!", false);
            }
            if (random == 2 && choice.equals("rock")) {
                embed.addField("You lose!", "\uD83E\uDEA8 VS \uD83D\uDCDC = you lose!", false);
            }
            if (random == 2 && choice.equals("scissors")) {
                embed.addField("You win!", "✂ VS \uD83D\uDCDC = you win!", false);
            }
            if (random == 3 && choice.equals("rock")) {
                embed.addField("You win!", "\uD83E\uDEA8 vs ✂ = you win!", false);
            }
            if (random == 3 && choice.equals("paper")) {
                embed.addField("You lose!", "\uD83D\uDCDC VS ✂ = you lose!", false);
            }



            event.replyEmbeds(embed.build()).queue();



        }
    }
}
