package com.sourcey.materiallogindemo.LoggedUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {
    /*
     * I used singleton design pattern.
     * */
    private static SharedPref myInstance = new SharedPref();

    private SharedPref() {
    }

    public static SharedPref getMyInstance() {
        return myInstance;
    }

    public static Context context;
    private static final String MYPREFERENCES = "MyPreferences";
    private SharedPreferences sharedPreferences;
    private boolean recordType = false;
    private String processType;


    private int id;
    private String username;

    public String getUsername() {
        processType = "username";
        recordType = false;
        setGetSharedpreferences();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        processType = "username";
        recordType = true;
        setGetSharedpreferences();
    }

    public int getId() {
        processType = "id";
        recordType = false;
        setGetSharedpreferences();
        return id;
    }

    public void setId(int id) {
        this.id = id;
        processType = "id";
        recordType = true;
        setGetSharedpreferences();
    }


        private void setGetSharedpreferences() {
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (recordType) {
            /*
             * recordType=true
             * Data sets to shared preferences.
             * */
            switch (processType) {
                case "username":
                    editor.putString("username", username);
                    editor.apply();
                    break;
                case "id":
                    editor.putInt("id", id);
                    editor.apply();
                    break;
            }
        } else {
            /*
             * recordType=false
             * Data gets to shared preferences.
             * */
            switch (processType) {
                case "username":
                    username = sharedPreferences.getString("username", username);
                    break;
                case "id":
                    id = sharedPreferences.getInt("id", id);
                    break;
            }
        }
    }
}
