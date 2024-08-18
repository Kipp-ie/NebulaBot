package dev.kippie.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

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
                    if (!Files.exists(Path.of("Data/Economy/" + user.getId() + "/data.txt"))) {
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
                        Scanner moneyScanner = null;
                        try {
                            moneyScanner = new Scanner(Path.of("Data/Economy/" + user.getId() + "/data.txt"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String oldmoney = moneyScanner.nextLine();
                        int money = (int) (Integer.parseInt(oldmoney) * 0.75);
                        try {
                            myWriter.write(money);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int givenmoney = (Integer.parseInt(oldmoney) - money);
                        Scanner moneyScanner2 = null;
                        try {
                            moneyScanner2 = new Scanner(Path.of("Data/Economy/" + event.getUser().getId() + "/data.txt"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int oldmoney2 = Integer.parseInt(moneyScanner2.nextLine());
                        int newmoney = oldmoney2 + givenmoney;
                        FileWriter myWriter2 = null;
                        try {
                            myWriter2 = new FileWriter("Data/Economy/" + event.getUser().getId() + "/data.txt");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            myWriter2.write(newmoney);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        FileWriter myWriter = null;
                        try {
                            myWriter = new FileWriter("Data/Economy/" + user.getId() + "/data.txt");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Scanner moneyScanner = null;
                        try {
                            moneyScanner = new Scanner(Path.of("Data/Economy/" + user.getId() + "/data.txt"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String oldmoney = String.valueOf(moneyScanner.nextInt());
                        int money = (int) (Integer.parseInt(oldmoney) * 0.75);
                        try {
                            myWriter.write(money);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int givenmoney = (Integer.parseInt(oldmoney) - money);
                        Scanner moneyScanner2 = null;
                        try {
                            moneyScanner2 = new Scanner(Path.of("Data/Economy/" + event.getUser().getId() + "/data.txt"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int oldmoney2 = Integer.parseInt(moneyScanner2.nextLine());
                        int newmoney = oldmoney2 + givenmoney;
                        FileWriter myWriter2 = null;
                        try {
                            myWriter2 = new FileWriter("Data/Economy/" + event.getUser().getId() + "/data.txt");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            myWriter2.write(newmoney);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
                System.out.println(random);
            }
        }
    }
}
