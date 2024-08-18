package dev.kippie.listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OnEnable extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        System.out.println("Bot Enabled, checking data folder.");
        if (!Files.exists(Path.of("Data"))) {
            try {
                Files.createDirectory(Path.of("Data"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Data folder created.");

        } else {
            System.out.println("Data folder found.");
        }
        System.out.println("Checking for Economy Data folder");
        if (!Files.exists(Path.of("Data/Economy"))) {
            try {
                Files.createDirectory(Path.of("Data/Economy"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Economy data created.");

        } else {
            System.out.println("Economy folder found.");
        }
        System.out.println("Data checked, startup can proceed.");
    }
}
