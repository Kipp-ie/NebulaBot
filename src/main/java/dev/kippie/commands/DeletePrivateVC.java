package dev.kippie.commands;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeletePrivateVC extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("deleteprivatevc")) {
            if (event.getGuild().getVoiceChannelsByName("vc-" + event.getUser().getName(), true).isEmpty()) {
                event.reply("You don't have a private vc! Create one using /privatevc").setEphemeral(true).queue();
            } else {
                Channel vcsearch = event.getGuild().getVoiceChannelsByName("vc-" + event.getUser().getName(), true).getFirst();
                event.getGuild().getVoiceChannelById(vcsearch.getId()).delete().queue();
                event.reply("PrivateVC has been deleted").setEphemeral(true).queue();
            }
        }
    }
}
