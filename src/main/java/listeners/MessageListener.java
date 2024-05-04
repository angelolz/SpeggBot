package listeners;

import main.LoggerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class MessageListener extends ListenerAdapter {
    private static Random rng;
    private static long lastResponded;

    public MessageListener() {
        rng = new Random();
        lastResponded = 0;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        try {

            String selfMention = event.getJDA().getSelfUser().getAsMention();

            if (event.getMessage().getContentRaw().contains(selfMention)        //check if its been pinged
                && System.currentTimeMillis() - lastResponded >= 300000         //check if its been 5 minutes since last response
                && rng.nextDouble() <= 0.35) {                                  //35% chance of happening
                List<String> responses = Files.readAllLines(Path.of("responses.txt"));
                event.getMessage().reply(responses.get(rng.nextInt(responses.size()))).queue();
                lastResponded = System.currentTimeMillis();
            }
        } catch (IOException e) {
            LoggerManager.getLogger().error("Error getting a response, {}", e.getMessage());
        }
    }
}
