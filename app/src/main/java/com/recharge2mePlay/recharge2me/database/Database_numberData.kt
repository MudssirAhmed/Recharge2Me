package com.recharge2mePlay.recharge2me.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import local_Databasse.entity_numberDetails

@Database(entities = [entity_numberDetails::class], version = 1, exportSchema = false)
abstract class Database_numberData: RoomDatabase() {

    abstract fun numberDao(): Dao_numberDetails

    companion object {

        @Volatile
        private var INSTANCE: Database_numberData? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): Database_numberData {

            val tempInstance = INSTANCE

            if(tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        Database_numberData::class.java,
                        "numberDetails"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }


}
