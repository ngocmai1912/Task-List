package com.nmai.todo

import android.util.Log

object Data{
    var listHome = mutableListOf<Task>()
    var listDone = mutableListOf<Task>()
    var listImportant = mutableListOf<Task>()
    lateinit var db : DataTask
    var checkDelete : Boolean = false
    fun loadListData(){ //load du lieu tu sqlite
        listHome.clear()
        listImportant.clear()
        listDone.clear()
        Log.d("applog", "load data")
        val cursor = db.getAllRow()
        cursor!!.moveToFirst()
        while(!cursor.isAfterLast){
            var task = Task("2", 0, 0)
            task.id = cursor.getInt(cursor.getColumnIndex(DataTask.COLUMN_ID))
            task.content = cursor.getString(cursor.getColumnIndex(DataTask.COLUMN_CONTENT))
            task.isDone = cursor.getInt(cursor.getColumnIndex(DataTask.COLUMN_ISDONE))
            task.isImportant = cursor.getInt(cursor.getColumnIndex(DataTask.COLUMN_ISIMPORTANT))
            if(task.isImportant==1) listImportant.add(task)
            if(task.isDone==1) listDone.add(task)
            else listHome.add(task)
            cursor.moveToNext()
        }
    }
}
interface FragmentInterface{
    fun fragmentBecameVisible()
}