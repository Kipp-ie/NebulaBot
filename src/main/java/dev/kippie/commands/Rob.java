package dev.kippie.commands;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;

public class Rob extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("rob")) {
            User user = event.getOption("user").getAsUser();
            if (user.getId().equals(event.getUser().getId())) {
                event.reply("Real funny but you can't rob yourself!").setEphemeral(true).queue();
            } else {
                if (event.getMember().getRoles().contains(event.getGuild().getRoleById("1274858961375592478"))) {
                    event.reply("Sorry! You can't rob someone while you are in jail!").setEphemeral(true).queue();
                }
                int random = (int) (Math.random() * 100 + 1);
                if (random > 75) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("You got caught by the police while robbing " + user.getName() + "!");
                    embed.setDescription("You are now in jail! Wait for someone to bail you out, or work your way out of prison! Check your dm's for more information!");
                    embed.setImage("https://api.popcat.xyz/jail?image=" + event.getUser().getAvatarUrl());
                    Dotenv dotenv = Dotenv.load();
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(event.getUser(), Objects.requireNonNull(event.getGuild().getRoleById("1274858961375592478"))).queue();
                    event.replyEmbeds(embed.build()).queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Wphew you robbed " + user.getName() + "!");
                    Dotenv dotenv = Dotenv.load();
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    embed.setDescription("You managed to get 25% of his cash! Good job!");
                    event.replyEmbeds(embed.build()).queue();
                    MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
                    MongoDatabase database = mongoClient.getDatabase("users");
                    MongoCollection<Document> collection = database.getCollection("users");
                    Document doc = collection.find(eq("id", user.getId())).first();
                    if (doc == null) {
                        collection.insertOne(new Document("id", user.getId()));
                        Bson updates = Updates.combine(
                                Updates.set("money", "100")
                        );
                        collection.updateOne(Objects.requireNonNull(collection.find(eq("id", user.getId())).first()), updates);

                    }
                    Document doc2 = collection.find(eq("id", user.getId())).first();
                    int oldMoney = Integer.parseInt(doc2.get("money").toString());
                    int newMoney = (int) (oldMoney * 0.75);

                    int givenmoney = oldMoney - newMoney;

                    Document doc3 = collection.find(eq("id", event.getUser().getId())).first();
                    if (doc3 == null) {
                        collection.insertOne(new Document("id", event.getUser().getId()));
                        Bson updates = Updates.combine(
                                Updates.set("money", "100")
                        );
                        collection.updateOne(Objects.requireNonNull(collection.find(eq("id", user.getId())).first()), updates);

                    }
                    Document doc4 = collection.find(eq("id", event.getUser().getId())).first();
                    int currentmoney = Integer.parseInt(doc4.get("money").toString());
                    int moneyGivenCombined = givenmoney + currentmoney;
                    Bson updates = Updates.combine(
                            Updates.set("money", moneyGivenCombined)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);

                    Bson updates2 = Updates.combine(
                            Updates.set("money", newMoney)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", user.getId())).first()), updates2);



                }
}}}}
