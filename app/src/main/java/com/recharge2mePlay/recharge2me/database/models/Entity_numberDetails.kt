package local_Databasse

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "numberDetails")
data class entity_numberDetails(

        @PrimaryKey
        val number: String,
        val circle: String,
        val operator: String,

): Serializable
