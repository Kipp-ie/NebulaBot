package dev.kippie.commands;
import static com.mongodb.client.model.Filters.eq;
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


import static javax.management.Query.eq;

public class Money extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("money")) {
            Dotenv dotenv = Dotenv.load();
            User user = event.getOption("user").getAsUser();
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

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Balance of " + user.getName());
                embed.addField("Balance", Objects.requireNonNull(collection.find(eq("id", user.getId())).first().get("money")).toString(), false);
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                String name = dotenv.get("BOT_NAME");
                embed.setFooter(name);
                event.replyEmbeds(embed.build()).queue();
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Balance of " + user.getName());
                embed.addField("Balance", Objects.requireNonNull(collection.find(eq("id", user.getId())).first().get("money").toString()), false);
                String color = dotenv.get("COLOR");
                embed.setColor(Color.decode(color));
                String name = dotenv.get("BOT_NAME");
                embed.setFooter(name);
                event.replyEmbeds(embed.build()).queue();
            }


        }
    }
}