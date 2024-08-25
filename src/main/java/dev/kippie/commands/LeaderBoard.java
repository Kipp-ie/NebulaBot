package dev.kippie.commands;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.util.Objects;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.eq;

public class LeaderBoard extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("leaderboard")) {
            Dotenv dotenv = Dotenv.load();
            MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find().sort(new BasicDBObject("points",1)).limit(1).first();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Leaderboard King");
            System.out.println(doc.get("id").toString());
            User user = event.getGuild().getMemberById(doc.get("id").toString()).getUser();
            embed.addField("Number 1", user.getName(), false);

            event.replyEmbeds(embed.build()).queue();

        }
    }
}
