package commands.admincommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import listeners.MessageListener;
import utils.ConvertTime;

import java.util.Map;

public class Debug extends Command
{
    public Debug()
    {
        this.name = "debug";
        this.ownerCommand = true;
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String[] args = event.getArgs().split("\\s+");

        switch(args[0].toLowerCase())
        {
            case "cooldown" ->
            {
                StringBuilder sb = new StringBuilder();
                Map<String, Long> cooldowns = MessageListener.getCooldowns();

                if(cooldowns.isEmpty())
                {
                    event.reply(":x: | None.");
                    return;
                }

                for(Map.Entry<String, Long> cooldownEntry : cooldowns.entrySet())
                {
                    String name = event.getJDA().retrieveUserById(cooldownEntry.getKey()).complete().getEffectiveName();
                    String time = ConvertTime.convertMsToTime(System.currentTimeMillis() - cooldownEntry.getValue());

                    sb.append(String.format("**%s** - %s%n", name, time));
                }

                event.reply(sb.toString());
            }
        }
    }
}
