package main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.*;
import commands.admincommands.Debug;
import commands.admincommands.Invite;
import commands.admincommands.Talk;
import listeners.AmogusListener;
import listeners.MessageListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

public class BotManager
{
    public static void init()
    {
        //create command builders and listeners
        CommandClientBuilder client = new CommandClientBuilder();

        //bot client config
        client.useHelpBuilder(false);
        client.setActivity(Activity.playing("with ur mom lol | " + ConfigManager.getPrefix() + "help"));
        client.setOwnerId(ConfigManager.getOwnerId());
        client.setCoOwnerIds(ConfigManager.getCoOwnerId());
        client.setEmojis("✅", "⚠️", "❌");
        client.setPrefix(ConfigManager.getPrefix());

        //admin text commands
        client.addCommands(
            new Invite(),
            new Debug(),
            new Talk()
        );

        //slash commands here
        client.addSlashCommands(
            new Help(),
            new Ping()
        );

        //non-hidden commands
        client.addCommands(
            new Help(),
            new Ping()
        );

        // ONLY FOR TESTING, uncomment when testing
//        client.forceGuildOnly(ConfigManager.getTestingGuildId());

        DefaultShardManagerBuilder.createLight(ConfigManager.getToken())
                                  .setStatus(OnlineStatus.DO_NOT_DISTURB)
                                  .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                                  .setActivity(Activity.playing("loading!! | " + ConfigManager.getPrefix() + "help"))
                                  .addEventListeners(client.build(), new MessageListener(), new AmogusListener())
                                  .build();
    }
}
