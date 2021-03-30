package com.aarappstudios.todofromskillshare.data.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aarappstudios.todofromskillshare.data.ToDoDao
import com.aarappstudios.todofromskillshare.data.TodoDatabase
import com.aarappstudios.todofromskillshare.data.models.TodoData
import com.aarappstudios.todofromskillshare.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModal(application: Application) : AndroidViewModel(application) {

    private var todoDao: ToDoDao
    private val repository: ToDoRepository


    val getAllData: LiveData<List<TodoData>>
    val sortByHighPriority: LiveData<List<TodoData>>
    val sortByLowPriority: LiveData<List<TodoData>>

    init {
        todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = ToDoRepository(todoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHignPriority
        sortByLowPriority = repository.sortBylowPriority
    }

    fun insertData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(todoData)
        }
    }

    fun updateData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updataData(todoData)
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(id)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchAll(string: String): LiveData<List<TodoData>> {
        return repository.searchAll(string)
    }

}