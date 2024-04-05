package com.example.my_to_do_app.ui

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.getDateOnly():Long{
    val calendar = Calendar.getInstance()
    calendar.set(get(Calendar.YEAR),get(Calendar.MONTH),
        get(Calendar.DATE),0,0,0)
    calendar.set(Calendar.MILLISECOND,0)
    return calendar.time.time
}
fun Calendar.getTimeOnly():Long{
    val calendar = Calendar.getInstance()
    calendar.set(0,0,0,
        get(Calendar.HOUR_OF_DAY),
        get(Calendar.MINUTE),
        0)
    calendar.set(Calendar.MILLISECOND,0)
    return calendar.time.time
}
fun Calendar.formatTime():String{
    val formatter = SimpleDateFormat("hh:mm a")
    return formatter.format(time)
}
fun Calendar.formatDate():String{
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(time)
}