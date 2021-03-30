package com.aarappstudios.todofromskillshare.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aarappstudios.todofromskillshare.data.models.TodoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData():LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insertData(todoData: TodoData)

    @Update
    suspend fun updateData(todoData: TodoData)

    @Query("delete  from todo_table where id = :todoid ")
    suspend fun deleteData(todoid:Int)

    @Query("delete from todo_table")
    suspend fun deleteAll()

    @Query("select * from todo_table where title like :searchQuery")
    fun searchAll(searchQuery: String):LiveData<List<TodoData>>

    @Query("select * from todo_table order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority():LiveData<List<TodoData>>

    @Query("select * from todo_table order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun sortByLowPriority():LiveData<List<TodoData>>
}