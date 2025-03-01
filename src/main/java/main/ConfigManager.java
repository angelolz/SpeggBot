package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager
{
    private static final String PREFIX = "sp!";
    private static final String VERSION = "1.2.0";
    private static String token;
    private static String ownerId;
    private static String coOwnerId;
    private static String testingGuildId;

    public static void init() throws IOException
    {
        try(FileInputStream propFile = new FileInputStream("bot.properties"))
        {
            Properties prop = new Properties();
            prop.load(propFile);
            token = prop.getProperty("bot_token");
            ownerId = prop.getProperty("owner_id");
            coOwnerId = prop.getProperty("coowner_id");
            testingGuildId = prop.getProperty("testing_guild_id", "0");
        }
    }

    public static String getPrefix()
    {
        return PREFIX;
    }

    public static String getVersion()
    {
        return VERSION;
    }

    public static String getToken()
    {
        return token;
    }

    public static String getOwnerId()
    {
        return ownerId;
    }

    public static String getCoOwnerId() { return coOwnerId; }

    public static String getTestingGuildId()
    {
        return testingGuildId;
    }
}
