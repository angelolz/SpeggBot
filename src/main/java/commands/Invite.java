package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Invite extends Command
{
    public Invite() {
        this.name = "invite";
        this.ownerCommand = true;
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        event.reply("invite link [here](https://discord.com/oauth2/authorize?client_id=1237731151427403898&permissions=292058164288&scope=applications.commands+bot)");
    }
}
