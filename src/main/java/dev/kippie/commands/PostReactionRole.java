package dev.kippie.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Objects;

public class PostReactionRole extends ListenerAdapter {
    @Override

    @JsonInclude(JsonInclude.Include.NON_NULL)
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
            Button button1 = null;
            Button button2 = null;
            Button button3 = null;
            Button button4 = null;
            if (!role1id.isBlank() && !role1emoji.isBlank()) {
                embed.addField(event.getGuild().getRoleById(role1id).getName(), "Click on the " + Emoji.fromUnicode(role1emoji).getFormatted() + " emoji to get this role.", false);
                button1 = Button.primary("role1", Emoji.fromUnicode(role1emoji));
            }
            if (!role2id.isBlank() && !role2emoji.isBlank()) {
                embed.addField(event.getGuild().getRoleById(role2id).getName(), "Click on the " + Emoji.fromUnicode(role2emoji).getFormatted() + " emoji to get this role.", false);
                button2 = Button.primary("role2", Emoji.fromUnicode(role2emoji));
            }
            if (!role3id.isBlank() && !role3emoji.isBlank()) {
                embed.addField(event.getGuild().getRoleById(role3id).getName(), "Click on the " + Emoji.fromUnicode(role3emoji).getFormatted() + " emoji to get this role.", false);
                button3 = Button.primary("role3", Emoji.fromUnicode(role3emoji));
            }
            if (!role4id.isBlank() && !role4emoji.isBlank()) {
                embed.addField(event.getGuild().getRoleById(role4id).getName(), "Click on the " + Emoji.fromUnicode(role4emoji).getFormatted() + " emoji to get this role.", false);
                button4 = Button.primary("role4", Emoji.fromUnicode(role4emoji));
            }

            String color = dotenv.get("COLOR");
            embed.setDescription("Click on one of the buttons to assign a role to yourself, if you click a role again it removes that role.");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            event.getChannel().sendMessageEmbeds(embed.build())
                    .addActionRow(
                            button1,
                            button2,
                            button3,
                            button4

                    ).queue(message -> {
                        event.reply("Reaction Role posted").setEphemeral(true).queue();
                    });


    }}

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("role1")) {

            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(dotenv.get("ROLE1")))) {
                event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE1")))).queue();
                event.reply("You got the " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE1"))).getName() + " role!").setEphemeral(true).queue();
            } else {
                event.getGuild().removeRoleFromMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE1")))).queue();
                event.reply("The " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE1"))).getName() + " role has been removed.").setEphemeral(true).queue();
            }


        } else if (event.getComponentId().equals("role2")) {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(dotenv.get("ROLE2")))) {
                event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE2")))).queue();
                event.reply("You got the " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE2"))).getName() + " role!").setEphemeral(true).queue();
            } else {
                event.getGuild().removeRoleFromMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE2")))).queue();
                event.reply("The " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE2"))).getName() + " role has been removed.").setEphemeral(true).queue();
            }


        } else if (event.getComponentId().equals("role3")) {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(dotenv.get("ROLE3")))) {
                event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE3")))).queue();
                event.reply("You got the " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE3"))).getName() + " role!").setEphemeral(true).queue();
            } else {
                event.getGuild().removeRoleFromMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE3")))).queue();
                event.reply("The " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE3"))).getName() + " role has been removed.").setEphemeral(true).queue();
            }

        } else if (event.getComponentId().equals("role4")) {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(dotenv.get("ROLE4")))) {
                event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE4")))).queue();
                event.reply("You got the " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE4"))).getName() + " role!").setEphemeral(true).queue();
            } else {
                event.getGuild().removeRoleFromMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(dotenv.get("ROLE4")))).queue();
                event.reply("The " + Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getRoleById(dotenv.get("ROLE4"))).getName() + " role has been removed.").setEphemeral(true).queue();
            }
        }

    }
}
