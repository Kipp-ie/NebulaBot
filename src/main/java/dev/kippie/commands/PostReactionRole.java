package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class PostReactionRole extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("postreactionrole")) {
            Dotenv dotenv = Dotenv.load();
            String role1id = dotenv.get("ROLE1");
            String role2id = dotenv.get("ROLE2");
            String role3id = dotenv.get("ROLE3");
            String role4id = dotenv.get("ROLE4");
            String role1emoji = dotenv.get("ROLE1EMOJI");
            String role2emoji = dotenv.get("ROLE2EMOJI");
            String role3emoji = dotenv.get("ROLE3EMOJI");
            String role4emoji = dotenv.get("ROLE4EMOJI");
            Channel eventchannel = event.getChannel();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("React for roles.");
            if (!role1id.isBlank() && !role1emoji.isBlank()) {
                embed.addField(event.getGuild().getRoleById(role1id).getName(), "Click on the " + Emoji.fromUnicode(role1emoji).getFormatted() + " emoji to get this role.", false);
            }

            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            event.getChannel().sendMessageEmbeds(embed.build())
                    .addActionRow(
                            Button.primary("role1", Emoji.fromUnicode(role1emoji) )
                    ).queue();


    }}

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("role1")) {
            System.out.println("Role1");
            event.reply("You got the " + event.getGuild().getRoleById(dotenv.get("ROLE1")).getName() + " role!").setEphemeral(true).queue();


        } else if (event.getComponentId().equals("role2")) {


        } else if (event.getComponentId().equals("role3")) {

        } else if (event.getComponentId().equals("role4")) {

        }

    }
}
