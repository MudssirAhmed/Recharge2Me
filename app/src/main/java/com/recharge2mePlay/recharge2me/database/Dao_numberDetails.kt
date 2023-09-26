package com.recharge2mePlay.recharge2me.database

import androidx.lifecycle.LiveData
import androidx.room.*
import local_Databasse.entity_numberDetails

@Dao
interface Dao_numberDetails {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add_numberDetails(nDet: entity_numberDetails)

    @Update
    suspend fun updateNumberData(nDet: entity_numberDetails)

    @Query("SELECT * FROM numberDetails")
    fun readAllData_numberDetails(): LiveData<List<entity_numberDetails>>

    @Query("SELECT number FROM numberDetails WHERE number = :compareNumber")
    fun getOneData(compareNumber: String): String

}