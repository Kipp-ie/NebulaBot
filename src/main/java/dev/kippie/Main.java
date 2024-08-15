package dev.kippie;


import dev.kippie.commands.Avatar;
import dev.kippie.commands.Nokia;
import dev.kippie.commands.Ping;
import dev.kippie.commands.WYR;
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
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.watching("Help me!"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(
                new slashcommandmanager(),
                new Ping(),
                new Avatar(),
                new Nokia(),
                new WelcomeListener(),
                new WYR()
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