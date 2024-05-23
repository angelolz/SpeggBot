package listeners;

import main.LoggerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import utils.Statics;

import java.io.File;
import java.util.Random;

public class SozekListener extends ListenerAdapter
{
    private static long lastSozekMomentPosted = 0;
    private static final Random rng = new Random();
    private File[] sozekMoments;
    private int index;

    public SozekListener()
    {
        File sozekFolder = new File("img/sozek");
        if(!sozekFolder.exists())
        {
            LoggerManager.getLogger().error("Couldn't find sozek moments folder!");
            return;
        }

        File[] files = sozekFolder.listFiles();
        if(files == null || files.length == 0)
        {
            LoggerManager.getLogger().warn("No loaded sozek moments.");
            return;
        }

        randomize(files);
        this.sozekMoments = files;
        this.index = 0;
        LoggerManager.getLogger().info("{} sozek moments loaded and randomized.", sozekMoments.length);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String userId = event.getAuthor().getId();

        if(userId.equals(Statics.SOZEK_ID) || userId.equals(Statics.SOZEK_ALT_ID))
            sendFunnySozekMoment(event);
    }


    private void sendFunnySozekMoment(MessageReceivedEvent event)
    {
        if(System.currentTimeMillis() - lastSozekMomentPosted < Statics.SOZEK_MOMENT_COOLDOWN_MS || rng.nextDouble() > 0.0122)
            return;

        FileUpload fu = FileUpload.fromData(sozekMoments[index]);

        lastSozekMomentPosted = System.currentTimeMillis();
        index++;

        if(index == sozekMoments.length)
        {
            randomize(sozekMoments);
            index = 0;
            LoggerManager.getLogger().info("Reshuffled all sozek moments.");
        }

        event.getChannel().sendFiles(fu).queue();
    }

    public static long getLastSozekMomentPosted()
    {
        return lastSozekMomentPosted;
    }

    public static void randomize(File[] files)
    {
        for(int i = files.length - 1; i > 0; i--)
        {
            int j = rng.nextInt(i + 1);

            File temp = files[i];
            files[i] = files[j];
            files[j] = temp;
        }
    }
}
