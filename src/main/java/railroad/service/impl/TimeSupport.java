package railroad.service.impl;

public class TimeSupport {

    /**
     * Transforms string which contains time in format HH:mm... to long
     *
     * @param stringTime string which contains time in format HH:mm...
     */
    public static long TimeToLong(String stringTime) {
        return (3600000 * Integer.parseInt("" + stringTime.charAt(0) + stringTime.charAt(1)) + 60000 * Integer.parseInt("" + stringTime.charAt(3) + stringTime.charAt(4)));
    }

    /**
     * Transforms long to string which contains time in format HH:mm
     *
     * @param longTime time in ms
     */
    public static String LongToTime(long longTime) {
        if (longTime < 0)
            longTime += 86400000;
        String part1 = Long.toString(longTime / 3600000);
        if (part1.length() == 1)
            part1 = "0" + part1;
        String part2 = Long.toString((longTime / 60000) % 60);
        if (part2.length() == 1)
            part2 = "0" + part2;
        return part1 + ":" + part2;
    }

}
