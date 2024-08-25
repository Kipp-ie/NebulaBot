package dev.kippie.commands;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.awt.*;

import static com.mongodb.client.model.Filters.eq;

public class Points extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("points")) {
            Dotenv dotenv = Dotenv.load();
            MongoClient mongoClient = MongoClients.create(dotenv.get("MONGODB_URI"));
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("id", event.getOption("user").getAsUser().getId())).first();

            if (doc == null) {
                event.reply("This person hasn't said anything yet!").queue();
            } else {
                if (doc.get("points") == null) {
                    event.reply("This person hasn't said anything yet!").queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(event.getOption("user").getAsUser().getName() + "'s point balance");
                    embed.addField("Balance", doc.get("points").toString(), false);
                    String color = dotenv.get("COLOR");
                    embed.setColor(Color.decode(color));
                    String name = dotenv.get("BOT_NAME");
                    embed.setFooter(name);
                    event.replyEmbeds(embed.build()).queue();

                }
            }


        }
    }
}
