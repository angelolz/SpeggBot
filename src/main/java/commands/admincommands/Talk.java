package commands.admincommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Talk extends Command
{
    public Talk() {
        this.name = "talk";
        this.hidden = true;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String[] args = event.getArgs().split("\\s+", 2);
        event.getJDA().getChannelById(TextChannel.class, args[0]).sendMessage(args[1]).queue();
    }
}
