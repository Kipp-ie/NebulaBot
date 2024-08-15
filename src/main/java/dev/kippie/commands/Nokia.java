package dev.kippie.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Nokia extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("nokia")) {
            event.reply("https://api.popcat.xyz/nokia?image=" + event.getUser().getAvatarUrl()).queue();
        }
    }
}
