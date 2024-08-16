package dev.kippie.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Dotenv dotenv = Dotenv.load();
        String welcome_enabled = dotenv.get("WELCOME");
        if (welcome_enabled.equals("true")) {
            String channelid = dotenv.get("WELCOME_CHANNEL");
            String guildname1 = event.getGuild().getName();
            String guildname = guildname1.replaceAll("\\s", "+");
            event.getGuild().getTextChannelById(channelid).sendMessage("https://api.popcat.xyz/welcomecard?background=https://cdn.popcat.xyz/welcome-bg.png&text1=" + event.getUser().getName() + "&text2=Welcome+To+" + guildname + "&text3=Member+" + event.getGuild().getMemberCount() + "&avatar=" + event.getUser().getAvatarUrl()).queue();
        }
        }}


