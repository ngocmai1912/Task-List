package com.nmai.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_important.*
import java.io.Serializable

class Fragment_important : Fragment(), FragmentInterface{
    lateinit var recyImportant : RecyclerView
    lateinit var adapter: ListTaskAdapter
    lateinit var imNoTask : ImageView
    lateinit var textNoTask : TextView
    val dialogDelete = DialogDelete()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("logim", "creat important")
        return inflater.inflate(R.layout.activity_important, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("logim", "view create")
        super.onViewCreated(view, savedInstanceState)
        recyImportant = view.findViewById(R.id.recyImportant)
        adapter = ListTaskAdapter(this.context)
        imNoTask = view.findViewById(R.id.imNotaskImportant)
        textNoTask = view.findViewById(R.id.textNoTaskImportant)
        recyImportant.apply {
            layoutManager = LinearLayoutManager(this@Fragment_important.context)
            adapter = this@Fragment_important.adapter
        }
        adapter.update(Data.listImportant)
        adapter.onClickDone = {
            adapter.isDone(it)
            Data.db.updateTask(it.id.toString(), it)
            if(it.isDone==1){
                Data.listDone.add(it)
                Toast.makeText(context, "Đã thêm task vào mục done", Toast.LENGTH_SHORT).show()
            }
            else{
                Data.listDone.remove(it)
                Toast.makeText(context, "Đã xóa task khỏi mục done", Toast.LENGTH_SHORT).show()
            }
        }
        adapter.onClickImportant = {
            adapter.isImportant(it)
            Data.db.updateTask(it.id.toString(), it)
            Data.listImportant.remove(it)
            if(Data.listImportant.size==0){
                imNoTask.setBackgroundResource(R.drawable.no_task)
                textNoTask.setText("No tasks")
            }
            Toast.makeText(context, "Đã xóa task khỏi mục important", Toast.LENGTH_SHORT).show()
            deleteTask(it)
        }
        adapter.longClick = {
            dialogDelete.show(childFragmentManager, null)
            dialogDelete.taskDelete = it
            dialogDelete.adapter = adapter
        }
        if(Data.listImportant.size==0){
            imNoTask.setBackgroundResource(R.drawable.no_task)
            textNoTask.setText("No tasks")
        }
    }

    override fun fragmentBecameVisible() {
        Data.loadListData()
        adapter.update(Data.listImportant)
    }
    fun newTask(task: Task){
        adapter.newTask(task)
    }
    fun deleteTask(task : Task){
        adapter.deleteTask(task)
    }
}