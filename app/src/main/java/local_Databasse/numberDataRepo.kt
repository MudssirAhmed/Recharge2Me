package local_Databasse

import androidx.lifecycle.LiveData

class numberDataRepo(private val numberDao: Dao_numberDetails) {

    val readAllDataNumber: LiveData<List<entity_numberDetails>> = numberDao.readAllData_numberDetails()

    suspend fun addNumberData(numberData: entity_numberDetails){
        numberDao.add_numberDetails(numberData)
    }

    suspend fun updateNumberData(numberData: entity_numberDetails){
        numberDao.updateNumberData(numberData)
    }

    suspend fun getOneData(number: String): String{
        val num: String = numberDao.getOneData(number)
        return num
    }
}