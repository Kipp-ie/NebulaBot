package dev.kippie.managers;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class slashcommandmanager extends ListenerAdapter {
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash( "ping", "Test the Bot"));
        commands.add(Commands.slash("avatar", "Get your avatar!"));
        commands.add(Commands.slash("nokia", "Turn yourself into a Nokia?"));
        commands.add(Commands.slash("wyr", "Post a would you rather"));

        event.getGuild().updateCommands().addCommands(commands).queue();

    }
}
