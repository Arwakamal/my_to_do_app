package com.example.my_to_do_app

import android.app.Application
import com.example.my_to_do_app.database.model.MyDataBase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyDataBase.init(this)
    }
}