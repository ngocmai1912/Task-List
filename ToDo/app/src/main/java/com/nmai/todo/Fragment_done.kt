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

class Fragment_done : Fragment(), FragmentInterface{
    lateinit var adapter: ListTaskAdapter
    lateinit var recyDone: RecyclerView
    lateinit var imDone : ImageView
    lateinit var textDone : TextView
    val dialogDelete = DialogDelete()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyDone = view.findViewById(R.id.recyDone)
        adapter = ListTaskAdapter(this.context)
        imDone = view.findViewById(R.id.imNotaskDone)
        textDone = view.findViewById(R.id.textNoTaskDone)
        recyDone.apply {
            layoutManager = LinearLayoutManager(this@Fragment_done.context)
            adapter = this@Fragment_done.adapter
        }
        adapter.update(Data.listDone)
        adapter.onClickDone = {
            adapter.isDone(it)
            Data.db.updateTask(it.id.toString(), it)
            Data.listDone.remove(it)
            Data.listHome.add(it)
            if(Data.listDone.size==0){
                imDone.setBackgroundResource(R.drawable.no_task)
                textDone.setText("No tasks")
            }
            Toast.makeText(context, "Đã xóa task khỏi mục done", Toast.LENGTH_SHORT).show()
            deleteTask(it)
        }
        adapter.onClickImportant = {
            adapter.isImportant(it)
            Data.db.updateTask(it.id.toString(), it)
            if(it.isImportant==1){
                Data.listImportant.add(it)
                Toast.makeText(context, "Đã thêm task vào mục important", Toast.LENGTH_SHORT).show()
            }
            else Data.listImportant.remove(it)
        }
        adapter.longClick = {
            dialogDelete.show(childFragmentManager, null)
            dialogDelete.taskDelete = it
            dialogDelete.adapter = adapter
        }
        if(Data.listDone.size==0){
            imDone.setBackgroundResource(R.drawable.no_task)
            textDone.setText("No tasks")
        }
    }

    override fun fragmentBecameVisible() {
        Data.loadListData()
        adapter.update(Data.listDone)
    }
    fun newTask(task: Task){
        adapter.newTask(task)
    }
    fun deleteTask(task : Task){
        adapter.deleteTask(task)
    }
}