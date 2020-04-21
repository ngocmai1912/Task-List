package com.nmai.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataTask(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, 1){
    companion object{
        const val DATABASE_NAME = "task.db"
        const val TABLE_NAME = "Task"
        const val COLUMN_ID = "id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_ISDONE = "isDone"
        const val COLUMN_ISIMPORTANT = "isImportant"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME " +
                    "($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_CONTENT TEXT, $COLUMN_ISDONE INTEGER, $COLUMN_ISIMPORTANT INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTask(list : List<Task>) {
        for(i in list){
            val values = ContentValues()
            values.put(COLUMN_CONTENT, i.content)
            values.put(COLUMN_ISDONE, i.isDone)
            values.put(COLUMN_ISIMPORTANT, i.isImportant)
            val db = this.writableDatabase
            db.insert(TABLE_NAME, null, values)
            db.close()
        }

    }
    fun updateTask(row_id: String, task: Task) {
        val values = ContentValues()
        values.put(COLUMN_CONTENT, task.content)
        values.put(COLUMN_ISDONE, task.isDone)
        values.put(COLUMN_ISIMPORTANT, task.isImportant)

        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun deleteTask(row_id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }
    fun getAllRow(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

}