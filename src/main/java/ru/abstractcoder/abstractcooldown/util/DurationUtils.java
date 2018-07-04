package ru.abstractcoder.abstractcooldown.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public final class DurationUtils {

    private DurationUtils() {
    }

    private static Map<String, Duration> durationMap = new HashMap<>();

    static {
        durationMap.put("d", ChronoUnit.DAYS.getDuration());
        durationMap.put("h", ChronoUnit.HOURS.getDuration());
        durationMap.put("m", ChronoUnit.MINUTES.getDuration());
        durationMap.put("s", ChronoUnit.SECONDS.getDuration());
    }

    public static Duration parseDuration(String time) {
        Duration duration = Duration.ZERO;
        for (String s : time.toLowerCase().split(" ")) {
            for (String t : durationMap.keySet()) {
                if (!s.endsWith(t)) {
                    continue;
                }
                duration = duration.plus(durationMap.get(t).multipliedBy(Long.parseLong(s.substring(0, s.length() - 1))));
                break;
            }
        }
        return duration;
    }

    public static String formatDuration(Duration duration) {
        StringBuilder sb = new StringBuilder();
        long d = duration.toDays();
        if (d > 0) {
            int group = getDeclensionGroup(d);
            sb.append(d).append(" ").append(group == 1 ? "день" : group == 2 ? "дней" : "дня").append(" ");
            duration = duration.minusDays(d);
        }
        long h = duration.toHours();
        if (h > 0) {
            int group = getDeclensionGroup(h);
            sb.append(h).append(" ").append(group == 1 ? "час" : group == 2 ? "часов" : "часа").append(" ");
            duration = duration.minusHours(h);
        }
        long m = duration.toMinutes();
        if (m > 0) {
            int group = getDeclensionGroup(m);
            sb.append(m).append(" ").append(group == 1 ? "минута" : group == 2 ? "минут" : "минуты").append(" ");
            duration = duration.minusMinutes(m);
        }
        long s = duration.getSeconds();
        if (s > 0) {
            int group = getDeclensionGroup(s);
            sb.append(s).append(" ").append(group == 1 ? "секунда" : group == 2 ? "секунд" : "секунды").append(" ");
        }
        return sb.toString();
    }

    private static int getDeclensionGroup(long n) {
        long k = n % 10;
        return k == 0 || k >= 5 || (n >= 11 && n <= 20) ? 2 : k == 1 ? 1 : 3;
    }

}