package dev.kippie.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

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
            if (!dotenv.get("ITEM_1_ID").equals("") && !dotenv.get("ITEM_1_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_1_ID")).getName(), "item1") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_1_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_2_ID").equals("") && !dotenv.get("ITEM_2_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_2_ID")).getName(), "item2") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_2_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_3_ID").equals("") && !dotenv.get("ITEM_3_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_3_ID")).getName(), "item3") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_3_PRICE").toString() + " points.")).build();
            }
            if (!dotenv.get("ITEM_4_ID").equals("") && !dotenv.get("ITEM_4_PRICE").equals("")) {
                menu.addOptions(SelectOption.of(event.getGuild().getRoleById(dotenv.get("ITEM_4_ID")).getName(), "item4") // another way to create a SelectOption
                        .withDescription(dotenv.get("ITEM_4_PRICE").toString() + " points.")).build();
            }


            event.replyEmbeds(embed.build())
                    .addActionRow(
                            menu.build()
                    ).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        Dotenv dotenv = Dotenv.load();
        if (event.getComponentId().equals("shop")) {
            if (event.getValues().get(0).equals("item1")) {
                MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                MongoDatabase database = mongoClient.getDatabase("users");
                MongoCollection<Document> collection = database.getCollection("users");
                Document doc = collection.find(eq("id", event.getUser().getId())).first();

            } else if (event.getValues().get(0).equals("item2")) {
                MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                MongoDatabase database = mongoClient.getDatabase("users");
                MongoCollection<Document> collection = database.getCollection("users");
                Document doc = collection.find(eq("id", event.getUser().getId())).first();

            } else if (event.getValues().get(0).equals("item3")) {
                MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                MongoDatabase database = mongoClient.getDatabase("users");
                MongoCollection<Document> collection = database.getCollection("users");
                Document doc = collection.find(eq("id", event.getUser().getId())).first();

            } else if (event.getValues().get(0).equals("item4")) {
                MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                MongoDatabase database = mongoClient.getDatabase("users");
                MongoCollection<Document> collection = database.getCollection("users");
                Document doc = collection.find(eq("id", event.getUser().getId())).first();

                if (doc == null) {
                    event.reply("You don't have any points!").setEphemeral(true).queue();
                } else {
                    if (doc.get("points") == null) {
                        event.reply("You don't have any points!").setEphemeral(true).queue();
                    } else {
                        if (Integer.parseInt(dotenv.get("ITEM_4_PRICE").toString()) <= Integer.parseInt(doc.get("points").toString())) {
                            event.reply("You bought role4").setEphemeral(true).queue();
                            Integer points = Integer.parseInt(doc.get("points").toString()) - Integer.parseInt(dotenv.get("ITEM_4_PRICE").toString());

                            Bson updates = Updates.combine(
                                    Updates.set("points", points)
                            );
                            collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(dotenv.get("ITEM_4_ID"))).queue();
                        } else {
                            event.reply("You don't have enough points!").setEphemeral(true).queue();
                        }
                    }
                }
        }


        }
    }
}
