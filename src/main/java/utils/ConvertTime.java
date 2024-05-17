package utils;

public class ConvertTime
{
    public static String convertMsToTime(long time)
    {
        int minutes;
        int seconds;
        String result;

        seconds = (int) ((time / 1000) % 60);
        minutes = (int) (time / (1000 * 60));

        result = minutes + " minutes, " + seconds + " seconds";
        return result;
    }

    public static long getTimeLeft(long cooldownTime, long currentCooldown)
    {
        return Math.max(cooldownTime - (System.currentTimeMillis() - currentCooldown), 0);
    }
}
