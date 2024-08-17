package dev.kippie.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class OnJoin extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        Dotenv dotenv = Dotenv.load();
        String botname = dotenv.get("BOT_NAME");
        embed.setTitle("Hi, i'm " + botname);
        embed.addField("NebulaBot", "I was made using NebulaBot https://github.com/Kipp-ie/nebulabot", false);
        embed.addField("Features", "All my features can be found at https://github.com/Kipp-ie/nebulabot !", false);
        String color = dotenv.get("COLOR");
        embed.setColor(Color.decode(color));
        String name = dotenv.get("BOT_NAME");
        embed.setFooter(name);
        event.getGuild().getOwner().getUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed.build())).queue();
    }
}
