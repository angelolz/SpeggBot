package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import main.CommandTracker;
import main.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Help extends SlashCommand
{
    public Help()
    {
        this.name = "help";
        this.help = "Shows all commands available for this bot.";
        this.cooldown = 3;
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        CommandTracker.incrementSlashCount("help");

        event.getJDA().retrieveUserById(event.getClient().getOwnerId()).queue(
            user -> {
                MessageEmbed embed = getEmbed(user, event.getJDA().getSelfUser().getAvatarUrl(), event.getClient().getCommands());
                event.replyEmbeds(embed).queue();
            }
        );
    }

    @Override
    protected void execute(CommandEvent event)
    {
        CommandTracker.incrementTextCount("help");

        String ownerId = event.getClient().getOwnerId();

        event.getJDA().retrieveUserById(ownerId).queue(
            user -> {
                MessageEmbed embed = getEmbed(user, event.getSelfUser().getAvatarUrl(), event.getClient().getCommands());
                event.reply(embed);
            }
        );

    }

    private MessageEmbed getEmbed(User user, String botAvatarUrl, List<Command> commands)
    {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("SpeggBot")
             .setColor(0x32CD32)
             .setDescription("Here are a list of commands you can use! Please, **don't** ping me.")
             .setFooter(String.format("Created by: %s | Version: %s", user.getName(), ConfigManager.getVersion()))
             .setThumbnail(botAvatarUrl);

        for(Command command : commands)
        {
            if(!command.isHidden() && !command.isOwnerCommand())
            {
                String commandName = String.format("%s%s", ConfigManager.getPrefix(), command.getName());
                if(command.getAliases().length > 0)
                {
                    String[] aliases = command.getAliases();
                    for(String alias : aliases)
                        commandName = commandName.concat("/" + alias);
                }

                embed.addField(commandName, command.getHelp(), true);
            }
        }

        return embed.build();
    }
}
