package com.goutamrestaurant.sharedhelper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bubun Goutam on 6/22/2016.
 */
public class SharedData {
    private static final String SHARED_PREF = "shared_value";
    private static final String KEY_NUMBER = "total_number";
    private static final String KEY_AMOUNT = "total_amount";
    private SharedPreferences sp;
    private Context context;

    public SharedData(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SHARED_PREF,0);
    }

    public void setNumber(int number){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(KEY_NUMBER,number);
        edit.commit();
    }

    public int getNumber(){
        return sp.getInt(KEY_NUMBER,0);
    }

    public void setAmount(int amount){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(KEY_AMOUNT,amount);
        edit.commit();
    }

    public int getAmount(){
        return sp.getInt(KEY_AMOUNT,0);
    }
}
