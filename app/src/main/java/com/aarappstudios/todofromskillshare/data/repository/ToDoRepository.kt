package com.aarappstudios.todofromskillshare.data.repository

import androidx.lifecycle.LiveData
import com.aarappstudios.todofromskillshare.data.ToDoDao
import com.aarappstudios.todofromskillshare.data.models.TodoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData:LiveData<List<TodoData>> = toDoDao.getAllData()
    val sortByHignPriority:LiveData<List<TodoData>> = toDoDao.sortByHighPriority()
    val sortBylowPriority:LiveData<List<TodoData>> = toDoDao.sortByLowPriority()

    suspend fun insertData(todoData: TodoData){toDoDao.insertData(todoData)}
    suspend fun updataData(todoData: TodoData){toDoDao.updateData(todoData)}
    suspend fun deleteData(id:Int){toDoDao.deleteData(id)}
    suspend fun deleteAll(){toDoDao.deleteAll()}
    fun searchAll(s:String):LiveData<List<TodoData>> {return toDoDao.searchAll(s)}


}