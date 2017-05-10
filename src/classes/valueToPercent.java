package classes;

/**
 * Created by PsyhoZOOM@gmail.com on 4/24/17.
 */
public class valueToPercent {
    public static Double getValue(double value, double percentage) {
        double percent = value * percentage / 100;
        return percent;

    }

    public static Double getPercentage(double value, double oFvalue) {
        double percentage = value / oFvalue;
        double result = percentage * 100;
        return result;
    }
}
