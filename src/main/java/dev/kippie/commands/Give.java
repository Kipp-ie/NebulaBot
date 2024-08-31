package dev.kippie.commands;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class Give extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("give")) {
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
        }
    }
}
