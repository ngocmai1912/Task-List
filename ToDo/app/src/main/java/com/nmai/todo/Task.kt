package com.nmai.todo

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Task(
    var content : String,
    var isDone : Int = 0,
    var isImportant :Int = 0
) : Serializable{
    var id : Int = 1
}