package dev.kippie;


import dev.kippie.commands.*;
import dev.kippie.listeners.OnJoin;
import dev.kippie.listeners.WelcomeListener;
import dev.kippie.managers.slashcommandmanager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main {



    private ShardManager shardManager;

    public Main() throws LoginException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");
        String status = dotenv.get("STATUS");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.watching(status));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(
                new slashcommandmanager(),
                new Ping(),
                new Avatar(),
                new Nokia(),
                new WelcomeListener(),
                new WYR(),
                new Fact(),
                new Dog(),
                new Cat(),
                new SadCat(),
                new Alert(),
                new Ship(),
                new Drip(),
                new RPS(),
                new Ticket(),
                new OnJoin(),
                new Add()
        );


    }

    public static void main(String[] args) {
        try {
            Main bot = new Main();
        } catch (LoginException e) {
            System.out.println("Bot token invalid!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}