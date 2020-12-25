package local_Databasse.providersData;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Entity_providers.class}, version = 1, exportSchema = false)
public abstract class Database_providers extends RoomDatabase {

    public abstract Dao_providers providersDao();
    private static volatile Database_providers INSTANCE;

    public static Database_providers getInstance(Context context){
        if(INSTANCE == null){
            synchronized (Dao_providers.class){
                if(INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Database_providers.class, "providers").build();

                }
            }
        }
        return INSTANCE;
    }
}
