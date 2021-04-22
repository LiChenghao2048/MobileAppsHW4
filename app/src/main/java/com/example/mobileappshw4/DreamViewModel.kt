package com.example.mobileappshw4

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel (private val repository: DreamRepository) : ViewModel() {

    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()

    // we want to make sure that view model is running in its own scope
    // in the view model library, it has its own scope
    // viewModelScope
    // you want to launch a new coroutine to run each of the suspend function
    // from you repository. To use viewModelScope, it allows everything to run based on
    // their life cycles

    fun insert(dream: Dream) = viewModelScope.launch {
        repository.insert(dream)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun update(title: String, content: String, reflection: String, emotion: String, id: Int) = viewModelScope.launch {
        repository.update(title, content, reflection, emotion, id)
    }

    val returnedDream = MutableLiveData<Dream>()
    fun getDream(id: Int) {
        viewModelScope.launch {
            returnedDream.value = repository.getDream(id)
        }
    }

}

class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory{

    // override the create method to return the TaskViewModel

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)){
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}