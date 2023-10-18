package com.recharge2mePlay.recharge2me.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import local_Databasse.entity_numberDetails;

@Database(entities = {entity_numberDetails.class}, version = 1, exportSchema = false)
public abstract  class Database_numberJava extends RoomDatabase {

    public abstract Dao_numberDetails numberDao();
    private static volatile Database_numberJava INSTANCE;

    public static Database_numberJava getInstance(Context context){
        if(INSTANCE == null){
            synchronized (Dao_numberDetails.class){
                if(INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                                    Database_numberJava.class, "numberDetails")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
