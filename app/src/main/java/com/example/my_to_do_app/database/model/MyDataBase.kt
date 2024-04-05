package com.example.my_to_do_app.database.model

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_to_do_app.database.dao.TasksDao

@Database(entities = [Task::class], version = 2, exportSchema = true)
abstract class MyDataBase :RoomDatabase() {
    abstract fun getTaskDao(): TasksDao

    companion object{
        private const val DATABASE_NAME = "tasks_database"
        private var dataBase: MyDataBase? =null

        fun init(app: Application){
            dataBase = Room.databaseBuilder(
                app.applicationContext,
                MyDataBase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        fun getInstance(editTaskActivity: Context): MyDataBase{

            return dataBase!!
        }
    }
}