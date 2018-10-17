/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net;

/**
 * Created by sherlock on 14/10/17.
 */

public class UrlGenerator {

//   private static final String BASE_URL = "http://192.168.1.7:3000/outpass/v1/";
//   private static final String BASE_URL = "http://192.168.43.143:3000/outpass/v1/";
   private static final String BASE_URL = "https://api.iilmcet.com/outpass/v1/";

    public static String getUrl() {
        return BASE_URL;
    }

    public static String getUrl(String addOn) {
        return BASE_URL + addOn;
    }

    public static String getUrlProfilePic() {
        return BASE_URL + "profile-img";
    }

    public static String getUrlProfilePic(String uid) {
        return BASE_URL + "profile-img?uid=" + uid;
    }
}
