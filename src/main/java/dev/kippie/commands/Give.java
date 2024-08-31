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
            } else {
                Document doc2 = collection.find(eq("id", event.getUser().getId())).first();
                if (doc2 == null) {
                    collection.insertOne(new Document("id", user.getId()));
                    Bson updates = Updates.combine(
                            Updates.set("money", "100")
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);

                }
                Document doc3 = collection.find(eq("id", event.getUser().getId())).first();
                if (Integer.parseInt(doc3.get("money").toString()) >= event.getOption("amount").getAsInt()) {
                    collection.insertOne(new Document("id", user.getId()));
                    Bson updates = Updates.combine(
                            Updates.set("money", Integer.parseInt(doc3.get("money").toString()) - event.getOption("amount").getAsInt())
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates);

                    Document doc4 = collection.find(eq("id", user.getId())).first();
                    Bson updates2 = Updates.combine(
                            Updates.set("money", Integer.parseInt(doc4.get("money").toString()) + event.getOption("amount").getAsInt())
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getUser().getId())).first()), updates2);
                    event.reply("You gave " + event.getOption("amount").getAsInt() + " to " + event.getOption("user").getAsUser().getName()).queue();
                } else {
                    event.reply("You do not have enough money!").setEphemeral(true).queue();
                }

            }
        }
    }}