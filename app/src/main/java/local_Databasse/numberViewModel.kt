package local_Databasse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class numberViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<entity_numberDetails>>
    private val repository: numberDataRepo

    init {
        val numberDao = Database_numberData.getDatabase(application).numberDao()
        repository = numberDataRepo(numberDao)
        readAllData = repository.readAllDataNumber
    }

    fun addNumberDetails(entityNumberdetails: entity_numberDetails){
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNumberData(entityNumberdetails)
            }
    }

    fun updateNumberData(entityNumberdetails: entity_numberDetails){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNumberData(entityNumberdetails)
        }
    }
}