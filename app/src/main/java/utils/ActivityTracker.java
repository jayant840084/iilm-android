package utils;

/**
 * Created by sherlock on 17/2/17.
 */

public class ActivityTracker {

    private static boolean activityRunning;

    public static boolean isActivityRunning() {
        return activityRunning;
    }

    public static void setActivityRunning(boolean activityRunning) {
        ActivityTracker.activityRunning = activityRunning;
    }
}
