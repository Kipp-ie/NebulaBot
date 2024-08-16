package dev.kippie.managers;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class slashcommandmanager extends ListenerAdapter {
    public void onGuildReady(GuildReadyEvent event) {
        Dotenv dotenv = Dotenv.load();
        String avatar_enabled = dotenv.get("AVATAR");
        String fact_enabled = dotenv.get("FACT");
        String nokia_enabled = dotenv.get("NOKIA");
        String ping_enabled = dotenv.get("PING");
        String wyr_enabled = dotenv.get("WYR");
        String dog_enabled = dotenv.get("DOG");
        String cat_enabled = dotenv.get("CAT");
        List<CommandData> commands = new ArrayList<>();

        if (avatar_enabled.equals("true")) {
            commands.add(Commands.slash("avatar", "Get your avatar!"));
        }
        if (fact_enabled.equals("true")) {
            commands.add(Commands.slash("fact", "Post a random fact."));
        }
        if (nokia_enabled.equals("true")) {
            commands.add(Commands.slash("nokia", "Turn yourself into a Nokia?"));
        }
        if (ping_enabled.equals("true")) {
            commands.add(Commands.slash("ping", "Test the Bot"));
        }
        if (wyr_enabled.equals("true")) {
            commands.add(Commands.slash("wyr", "Post a would you rather"));
        }
        if (dog_enabled.equals("true")) {
            commands.add(Commands.slash("dog", "Get a random dog picture"));
        }
        if (cat_enabled.equals("true")) {
            commands.add(Commands.slash("cat", "Get a random cat picture"));
        }

        event.getGuild().updateCommands().addCommands(commands).queue();

    }
}
