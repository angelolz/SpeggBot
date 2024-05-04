package main;

public class DiscordBot
{
    public static void main(String[] args)
    {
        try
        {
            ConfigManager.init();
            BotManager.init();
        }

        catch(Exception e)
        {
            System.out.println("oops");
            e.printStackTrace();
        }
    }
}