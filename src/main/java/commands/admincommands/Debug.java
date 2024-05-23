package commands.admincommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import listeners.MessageListener;
import listeners.SozekListener;
import utils.ConvertTime;
import utils.Statics;

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
                String sb = getPingCooldownString(event) +
                    "\n" +
                    getSozekMomentString() +
                    "\n" +
                    getMeowCooldownString(event);
                event.reply(sb);
            }
        }
    }

    private String getPingCooldownString(CommandEvent event)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("**ping cooldowns**\n");
        Map<String, Long> cooldowns = MessageListener.getPingCoolDowns();
        if(cooldowns.isEmpty())
            sb.append("❌ None.\n");

        else
        {
            for(Map.Entry<String, Long> cooldownEntry : cooldowns.entrySet())
            {
                String name = event.getJDA().retrieveUserById(cooldownEntry.getKey()).complete().getEffectiveName();
                long msLeft = ConvertTime.getTimeLeft(Statics.PING_COOLDOWN_TIME_MS, cooldownEntry.getValue());

                if(msLeft == 0)
                    sb.append(String.format("**%s** - ✅%n", name));
                else
                    sb.append(String.format("**%s** - %s left%n", name, ConvertTime.convertMsToTime(msLeft)));
            }
        }

        return sb.toString();
    }

    private String getSozekMomentString()
    {
        long msLeft = ConvertTime.getTimeLeft(Statics.SOZEK_MOMENT_COOLDOWN_MS, SozekListener.getLastSozekMomentPosted());

        if(msLeft == 0)
            return "**sozek moment** - ✅\n";
        else
            return "**sozek moment**\n" + ConvertTime.convertMsToTime(msLeft) + "\n";
    }

    private String getMeowCooldownString(CommandEvent event)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("**meow cooldowns**\n");
        Map<String, Long> cooldowns = MessageListener.getMeowCoolDowns();
        if(cooldowns.isEmpty())
            sb.append("❌ None.\n");

        else
        {
            for(Map.Entry<String, Long> cooldownEntry : cooldowns.entrySet())
            {
                String name = event.getJDA().retrieveUserById(cooldownEntry.getKey()).complete().getEffectiveName();
                long msLeft = ConvertTime.getTimeLeft(Statics.MEOW_COOLDOWN_TIME_MS, cooldownEntry.getValue());

                if(msLeft == 0)
                    sb.append(String.format("**%s** - ✅%n", name));
                else
                    sb.append(String.format("**%s** - %s left%n", name, ConvertTime.convertMsToTime(msLeft)));
            }
        }

        return sb.toString();
    }
}
