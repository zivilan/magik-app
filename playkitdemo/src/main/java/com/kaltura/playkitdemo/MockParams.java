package com.kaltura.playkitdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tehilarozin on 15/11/2016.
 */

public class MockParams {

/*Ott Mock params: */
    public static final String PhoenixBaseUrl = "http://api-preprod.ott.kaltura.com/v4_1/api_v3/";//http://52.210.223.65:8080/v4_1/api_v3/";
    public static final int OttPartnerId = 198;

    //result of login with : [username: albert@gmail.com, pass: 123456]
    public static final String KS = "djJ8MTk4fPjQkM96OQ7N4GBL73vbOrbUMx7QNMEdoJ5kc6pLFCbgoTHIOAAmIO3ny2Ro0MnKMqGEGpRGM2fq5schRQ8PzqODmb0yegckE6qH5j9hqNig";

    public static final String MediaId = "258656";//frozen
    public static final String MediaId2 = "437800";//vild-wV
    public static final String MediaId3 = "259295";//the salt of earth

    public static final String MediaType = "media";

    public static final String Format = "Mobile_Devices_Main_HD";
    public static final String Format2 = "Mobile_Devices_Main_SD";
    public static String FrozenAssetInfo = "mock/phoenix.asset.get.258656.json";
//---------------------------------------

/*Ovp Mock params: */
    public static final String KalturaOvpBaseUrl = "http://www.kaltura.com/api_v3/";
    public static final int OvpPartnerId = 2209591; // sample partner id
    public static final String OvpLoginId = "tehila.rozin@kaltura.com";
    public static final String OvpPassword = "symbioza1*";
    public static final String EntryId = "1_1h1vsv3z";//frozen

    public enum UserType{Ott, Ovp}

    public static class UserFactory {

        static ArrayList<UserLogin> ottUsers;
        static ArrayList<UserLogin> ovpUsers;

        static {
            fillWithUsers();
        }

        public static UserLogin getUser(UserType type) {
            List<UserLogin> users = type == UserType.Ott ? ottUsers : ovpUsers;
            int Min = 0;
            int Max = users.size() - 1;

            int index = Min + (int) (Math.random() * ((Max - Min) + 1));
            return users.get(index);
        }

        static void fillWithUsers() {
            ottUsers = new ArrayList<>();
            ottUsers.add(new UserLogin("albert@gmail.com", "123456", 198));
            ottUsers.add(new UserLogin("betsy@gmail.com", "123456", 198));
            ottUsers.add(new UserLogin("Alfred@gmail.com", "123456", 198));
            ottUsers.add(new UserLogin("ziv.ilan@kaltura.com", "123456", 198));
            ottUsers.add(new UserLogin("itan@b.com", "123456", 198));

            ovpUsers = new ArrayList<>();
            ovpUsers.add(new UserLogin("tehila.rozin@kaltura.com", "abcd1234*", 2209591));
            ovpUsers.add(new UserLogin("ziv.ilan@kaltura.com", "abcd1234*", 1281471));
        }

        public static class UserLogin {
            public String username;
            public String password;
            public int partnerId;

            public UserLogin(String username, String password, int partnerId) {
                this.username = username;
                this.password = password;
                this.partnerId = partnerId;
            }
        }
    }
}
