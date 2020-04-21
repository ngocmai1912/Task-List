package com.nmai.todo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogDelete : DialogFragment() {
    lateinit var btnCancel : Button
    lateinit var btnDelete : Button
    lateinit var taskDelete : Task
    lateinit var adapter: ListTaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.dialog_delete, container, false)
        btnCancel = view.findViewById(R.id.delete_btncancel)
        btnDelete = view.findViewById(R.id.delete_btndelete)
        btnCancel.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                dismiss()
            }
        })
        btnDelete.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d("delete", "click delete")
                Data.db.deleteTask("${taskDelete.id.toString()}")
                adapter.deleteTask(taskDelete)
                dismiss()
            }
        })
        return view
    }
}