package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class PrivateVC extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("privatevc")) {
            Dotenv dotenv = Dotenv.load();
            List channels = event.getGuild().getChannels();
            if (event.getGuild().getVoiceChannelsByName("vc-" + event.getUser().getName(), true).isEmpty()) {
                event.getGuild().getCategoryById(dotenv.get("PRIVATEVC_CATEGORY")).createVoiceChannel("vc-" + event.getUser().getName()).queue(voiceChannel -> {
                    voiceChannel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL).queue();
                    voiceChannel.getManager().setUserLimit(1).queue();
                    event.reply("PrivateVC has been made, you can invite friends by right clicking on them and clicking Invite to PrivateVC in Apps. Use /privatevcclose to delete the privatevc. <#" + voiceChannel.getId() + ">").setEphemeral(true).queue();
                });

            } else {
                event.reply("You already have a private VC! Close the one you already have with /deleteprivatevc!").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if (event.getName().equals("Add user to private vc")) {
            if (event.getGuild().getVoiceChannelsByName("vc-" + event.getUser().getName(), true).isEmpty()) {
                event.reply("You don't have a privateVC! Create one by using /privatevc").setEphemeral(true).queue();
            } else {
                Channel vcsearch = event.getGuild().getVoiceChannelsByName("vc-" + event.getUser().getName(), true).getFirst();

                event.getGuild().getVoiceChannelById(vcsearch.getId()).getManager().setUserLimit(event.getGuild().getVoiceChannelById(vcsearch.getId()).getUserLimit() + 1).queue();
                Objects.requireNonNull(event.getGuild().getVoiceChannelById(vcsearch.getId())).upsertPermissionOverride(Objects.requireNonNull(event.getTargetMember())).grant(Permission.VIEW_CHANNEL).queue();
                event.reply("User has been invited to the PrivateVC").setEphemeral(true).queue();
                event.getTarget().openPrivateChannel().flatMap(channel -> channel.sendMessage("You have been invited to a PrivateVC <#" + vcsearch.getId() + ">" )).queue();
            }

        }
    }
}
