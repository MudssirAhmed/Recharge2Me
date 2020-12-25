package local_Databasse.providersData;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "providers")
public class Entity_providers {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "provider_Id")
    String providerId;

    @ColumnInfo(name = "provider_name")
    String providerName;

    public Entity_providers(int id, String providerId, String providerName){
        this.id = id;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }
}
