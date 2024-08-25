package dev.kippie.listeners;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class OnMessage extends ListenerAdapter {
    @Override

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            Dotenv dotenv = Dotenv.load();
            if (dotenv.get("POINTS").equals("true")) {


            MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("id", event.getMember().getId())).first();
            if (doc == null) {
                collection.insertOne(new Document("id", event.getMember().getId()));
                Bson updates = Updates.combine(
                        Updates.set("points", 1)
                );
                collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getMember().getId())).first()), updates);
            } else {
                if (doc.get("points") == null) {
                    Bson updates = Updates.combine(
                            Updates.set("points", 1)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getMember().getId())).first()), updates);
                } else {
                    Bson updates = Updates.combine(
                            Updates.set("points", Integer.parseInt(doc.get("points").toString()) + 1)
                    );
                    collection.updateOne(Objects.requireNonNull(collection.find(eq("id", event.getMember().getId())).first()), updates);
            }}}

        }
    }
}
