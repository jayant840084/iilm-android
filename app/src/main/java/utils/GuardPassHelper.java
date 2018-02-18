/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import models.GuardLogModel;

/**
 * Created by Jayant Singh on 22/1/18.
 */

public class GuardPassHelper {

    private static GuardLogModel guardLogModel = null;
    private static String message = null;

    public static GuardLogModel getGuardLogModel() {
        return guardLogModel;
    }

    public static void setGuardLogModel(GuardLogModel guardLogModel) {
        GuardPassHelper.guardLogModel = guardLogModel;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        GuardPassHelper.message = message;
    }
}
