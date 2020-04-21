package com.nmai.todo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import java.util.zip.Inflater

class NewTask : DialogFragment() {
    lateinit var btnCancel : Button
    lateinit var btnApply : Button
    lateinit var content : EditText
    var clickApply : (task:Task) -> Unit = {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.new_task, container, false)
        btnCancel = view.findViewById(R.id.new_btnCancel)
        btnApply = view.findViewById(R.id.new_btnApply)
        content = view.findViewById(R.id.new_content)
        view.minimumHeight
        btnCancel.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                dismiss()
            }
        })
        btnApply.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(v: View?) {
                if(content.text!=null){
                    val task = Task("${content.text}",0,0)
                    clickApply(task)
                }
                else Toast.makeText(context,"Chưa nhập nội dung cho task", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }

}