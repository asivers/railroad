package railroad.dao.impl;

public class TimeSupport {

    public static long TimeToLong(String stringTime) {
        return (3600000 * Integer.parseInt("" + stringTime.charAt(0) + stringTime.charAt(1)) + 60000 * Integer.parseInt("" + stringTime.charAt(3) + stringTime.charAt(4)));
    }

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
