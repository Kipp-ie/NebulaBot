package dev.kippie.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class Shop extends ListenerAdapter {
    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("shop")) {
            Dotenv dotenv = Dotenv.load();
            EmbedBuilder embed = new EmbedBuilder();
            String color = dotenv.get("COLOR");
            embed.setColor(Color.decode(color));
            String name = dotenv.get("BOT_NAME");
            embed.setFooter(name);
            embed.setTitle("Welcome to the shop!");
            StringSelectMenu.Builder menu = StringSelectMenu.create("shop");
            StringSelectMenu item1 = null;
            StringSelectMenu item2 = null;
            StringSelectMenu item3 = null;
            StringSelectMenu item4 = null;
            if (!dotenv.get("ITEM_1_ID").equals("") && !dotenv.get("ITEM_1_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_1_ID")).getName(), "role1") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_1_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_2_ID").equals("") && !dotenv.get("ITEM_2_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_2_ID")).getName(), "role2") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_2_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_3_ID").equals("") && !dotenv.get("ITEM_3_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_3_ID")).getName(), "role3") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_2_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_2_ID").equals("") && !dotenv.get("ITEM_2_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_2_ID")).getName(), "role4") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_2_PRICE").toString() + " points.")).build();
            }


            event.replyEmbeds(embed.build())
                    .addActionRow(
                            menu.build()
                    ).queue();
        }
    }
}
