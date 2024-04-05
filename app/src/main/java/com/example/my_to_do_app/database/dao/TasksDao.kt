package com.example.my_to_do_app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.my_to_do_app.database.model.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)
    @Update
    fun updateTask(task: Task)
    @Delete
    fun deleteTask(id: Task)

    @Query("select * from task")
    fun getAllTasks():MutableList<Task>

    @Query("select * from task where date = :date order by time ASC")
    fun getTasksByDate(date: Unit): MutableList<Task>
}