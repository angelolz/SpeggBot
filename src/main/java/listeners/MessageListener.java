package listeners;

import main.LoggerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import utils.Statics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MessageListener extends ListenerAdapter
{
    private final Random rng = new Random();
    private static final Map<String, Long> pingCoolDowns = new HashMap<>();
    private static final Map<String, Long> meowCoolDowns = new HashMap<>();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String selfMention = event.getJDA().getSelfUser().getAsMention();
        String userId = event.getAuthor().getId();

        if(event.getAuthor().isBot())
            return;

        if(event.getMessage().getContentRaw().contains(selfMention))
            executePing(event);



        if(event.getMessage().getContentRaw().contains(":3"))
            sendMeow(event);
    }

    private void executePing(MessageReceivedEvent event)
    {
        try
        {
            String userId = event.getAuthor().getId();
            if(inCooldown(userId, pingCoolDowns, Statics.PING_COOLDOWN_TIME_MS))
                return;
            if(rng.nextDouble() > 0.35)
                return;

            List<String> responses = Files.readAllLines(Path.of("responses.txt"));
            event.getMessage().reply(responses.get(rng.nextInt(responses.size()))).queue();
            pingCoolDowns.put(userId, System.currentTimeMillis());
        }

        catch(IOException e)
        {
            LoggerManager.getLogger().error("Error getting a response, {}", e.getMessage());
        }
    }



    private void sendMeow(MessageReceivedEvent event)
    {
        if(inCooldown(event.getAuthor().getId(), meowCoolDowns, Statics.MEOW_COOLDOWN_TIME_MS) || rng.nextDouble() > 0.25)
            return;

        String[] responses = Statics.meowResponses;
        event.getChannel().sendMessage(responses[rng.nextInt(responses.length)]).queue();
        meowCoolDowns.put(event.getAuthor().getId(), System.currentTimeMillis());
    }

    private boolean inCooldown(String userId, Map<String, Long> cooldownMap, long cooldown)
    {
        return cooldownMap.containsKey(userId) && System.currentTimeMillis() - cooldownMap.get(userId) <= cooldown;
    }

    public static Map<String, Long> getPingCoolDowns()
    {
        return pingCoolDowns;
    }


    public static Map<String, Long> getMeowCoolDowns()
    {
        return meowCoolDowns;
    }
}
