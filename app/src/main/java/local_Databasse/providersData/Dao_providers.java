package local_Databasse.providersData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface Dao_providers {

    @Insert
    void insertProvider(List<Entity_providers> provider);

    @Query("SELECT * FROM providers WHERE provider_name == :pName")
    Entity_providers getProvider(String pName);

}
