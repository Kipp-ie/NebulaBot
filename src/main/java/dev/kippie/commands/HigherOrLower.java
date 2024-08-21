package dev.kippie.commands;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class HigherOrLower extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("higherorlower")) {
            Dotenv dotenv = Dotenv.load();
            MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("id", event.getUser().getId())).first();
            int bet = Integer.parseInt(event.getOption("bet").getAsString());
            if (bet <= Integer.parseInt(doc.get("money").toString())) {
                int random = (int) (Math.random() * 10 + 1);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Higher Or Lower");
                embed.setDescription("Predict if the next number will be higher or lower. The numbers range from 1-10. If you win your bet will multiplied times 1.5! if you lose you will lose your bet.");
                embed.addField("Higher or Lower?", String.valueOf(random), false);
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                embed.setFooter(String.valueOf(bet));
                embed.setAuthor(String.valueOf(random));
                event.replyEmbeds(embed.build()).addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("higher", "Higher"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.danger("lower", "Lower")
                ).queue();


            } else {
                event.reply("You do not have enough money to bet " + Integer.parseInt(doc.get("money").toString()) + " coins!").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("higher")) {
            if (event.getInteraction().getUser().equals(event.getUser())) {

                int random = (int) (Math.random() * 10 + 1);
                if (random > Integer.parseInt(Objects.requireNonNull(event.getMessage().getEmbeds().getFirst().getAuthor()).toString())) {
                    Dotenv dotenv = Dotenv.load();
                    MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                    MongoDatabase database = mongoClient.getDatabase("users");
                    MongoCollection<Document> collection = database.getCollection("users");
                    Document doc = collection.find(eq("id", event.getUser().getId())).first();
                    int reward = (int) (Integer.parseInt(event.getMessage().getEmbeds().getFirst().getFooter().toString()) * 1.5);
                    int updatemoney = (int) (Integer.parseInt(doc.get("money").toString()) + reward);
                    Bson updates = Updates.combine(
                            Updates.set("money", updatemoney)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getMessage().getInteraction().getUser().getId())).first()), updates);
                    event.reply("You won! You have gotten " + reward + " coins!").queue();


                } else {
                    Dotenv dotenv = Dotenv.load();
                    MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                    MongoDatabase database = mongoClient.getDatabase("users");
                    MongoCollection<Document> collection = database.getCollection("users");
                    Document doc = collection.find(eq("id", event.getUser().getId())).first();
                    int bet = (int) (Integer.parseInt(event.getMessage().getEmbeds().getFirst().getFooter().toString()));
                    int updatemoney = (int) (Integer.parseInt(doc.get("money").toString()) - bet);
                    Bson updates = Updates.combine(
                            Updates.set("money", updatemoney)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getMessage().getInteraction().getUser().getId())).first()), updates);
                    event.reply("You lost. You lost " + bet + " coins").queue();

                }
            } else {
                event.reply("You can't interact with this button!").setEphemeral(true).queue();
            }


        } else if (event.getComponentId().equals("lower")) {

        }
    }
}
