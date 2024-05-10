package listeners;

import main.LoggerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MessageListener extends ListenerAdapter
{
    private final Random rng = new Random();
    private static Map<String, Long> cooldowns = new HashMap<>();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        try
        {
            String selfMention = event.getJDA().getSelfUser().getAsMention();
            String userId = event.getAuthor().getId();

            if(event.getAuthor().isBot())
                return;
            if(!event.getMessage().getContentRaw().contains(selfMention))
                return;
            if(cooldowns.containsKey(userId) && System.currentTimeMillis() - cooldowns.get(userId) <= 180000)
                return;
            if(rng.nextDouble() > 0.35)
                return;

            List<String> responses = Files.readAllLines(Path.of("responses.txt"));
            event.getMessage().reply(responses.get(rng.nextInt(responses.size()))).queue();
            cooldowns.put(userId, System.currentTimeMillis());
        }
        catch(IOException e)
        {
            LoggerManager.getLogger().error("Error getting a response, {}", e.getMessage());
        }
    }

    public static Map<String, Long> getCooldowns() {
        return cooldowns;
    }
}
