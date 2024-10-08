package dev.kippie.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

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

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Welcome in " + event.getGuild().getName() + "!");
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setDescription("Hiya, i'm " + name);
            embed.addField("Features", "Tickets, reaction roles, XP system, Economy and even more!", false);
            event.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed.build())
                    .addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://github.com/Kipp-ie/NebulaBot", "NebulaBot Repository").withEmoji(Emoji.fromUnicode("U+1F30C"))
                    )


            ).queue();
        }
        }}


