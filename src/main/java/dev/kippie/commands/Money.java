package dev.kippie.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Money extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("money")) {
            User user = event.getOption("user").getAsUser();
            if (!Files.exists(Path.of("Data/Economy/" + user.getId()))) {
                try {
                    Files.createDirectory(Path.of("Data/Economy/" + user.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Files.createFile(Path.of("Data/Economy/" + user.getId() + "/data.txt"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileWriter myWriter = null;
                try {
                    myWriter = new FileWriter("Data/Economy/" + user.getId() + "/data.txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    myWriter.write("100");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    myWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Balance of " + user.getName());
                embed.addField("Balance", "100$", false);
                event.replyEmbeds(embed.build()).queue();


            } else {
                Scanner moneyScanner = null;
                try {
                    moneyScanner = new Scanner(Path.of("Data/Economy/" + user.getId() + "/data.txt"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Balance of " + user.getName());
                embed.addField("Balance", moneyScanner.nextLine() + "$", false);
                event.replyEmbeds(embed.build()).queue();

            }
        }
    }
}
