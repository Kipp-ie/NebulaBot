package dev.kippie.commands;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class Wish extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("wish")) {
            Dotenv dotenv = Dotenv.load();

            MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("id", event.getUser().getId())).first();
            if (doc == null) {
                collection.insertOne(new Document("id", event.getUser().getId()));
                Bson updates = Updates.combine(
                        Updates.set("money", "99")
                );
                collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);
            } else {
                collection.insertOne(new Document("id", event.getUser().getId()));
                Bson updates = Updates.combine(
                        Updates.set("money", Integer.parseInt(doc.get("money").toString()) - 1)
                );
                collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);

            }
            event.reply("You have made a wish! (Don't tell anyone what it was). It costed 1 money.").queue();
        }
    }}
