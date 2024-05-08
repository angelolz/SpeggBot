package listeners;

import main.LoggerManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class AmogusListener extends ListenerAdapter
{
    private static final Random rng = new Random();

    private static final String[] emoteList = new String[]{
        "Banana:1010328353179443210",
        "Black:1010328354513235988",
        "Blue:1010328356073513061",
        "Brown:1010328357411508384",
        "Coral:1010328358585913474",
        "Cyan:1010328359424761939",
        "Gray:1010328361177976862",
        "Green:1010328362641788989",
        "Lime:1010328363887505478",
        "Yellow:1010328375593807923",
        "White:1010328374364876910",
        "Tan:1010328372980756520",
        "Rose:1010328371776979014",
        "Red:1010328370443190362",
        "Purple:1010328369012953118",
        "Pink:1010328367364587570",
        "Orange:1010328366165000212",
        "Maroon:1010328365040926901",
    };

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot()) return;

        try
        {
            List<String> triggers = Files.readAllLines(Paths.get("sussy.txt"));

            for(String trigger : triggers)
            {
                if(event.getMessage().getContentRaw().toLowerCase().contains(trigger))
                {
                    event.getMessage().addReaction(Emoji.fromFormatted(emoteList[rng.nextInt(emoteList.length)])).queue();
                    return;
                }
            }
        }

        catch(IOException e)
        {
            LoggerManager.getLogger().error("Couldn't get among us triggers: {}", e.getMessage());
        }
    }
}
