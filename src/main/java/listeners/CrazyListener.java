package listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CrazyListener extends ListenerAdapter
{
    Map<String, String> responses = new HashMap<>();

    public CrazyListener()
    {
        responses.put("crazy", "I was crazy once.");
        responses.put("crazy?", "I was crazy once.");
        responses.put("i was crazy once", "They locked me in a room.");
        responses.put("they locked me in a room", "A rubber room.");
        responses.put("a rubber room with rats", "And rats make me crazy!");
        responses.put("a rubber room", "A rubber room with rats.");
        responses.put("rats make me crazy", "Crazy?");
        responses.put("and rats make me crazy", "Crazy?");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentStripped().toLowerCase();
        if(responses.containsKey(message))
        {
            event.getChannel().sendMessage(responses.get(message)).queue();
            return;
        }

        for(Map.Entry<String, String> entry : responses.entrySet())
        {
            if(message.startsWith(entry.getKey()))
            {
                event.getChannel().sendMessage(entry.getValue()).queue();
                return;
            }
        }
    }
}
