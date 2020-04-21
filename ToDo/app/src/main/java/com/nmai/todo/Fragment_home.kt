package com.nmai.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class Fragment_home : Fragment(), FragmentInterface{
    lateinit var adapter: ListTaskAdapter
    lateinit var recyHome: RecyclerView
    lateinit var btnAdd : Button
    lateinit var imHome : ImageView
    lateinit var textHome : TextView
    lateinit var task : Task
    val dialogNewTask = NewTask()
    val dialogDelete = DialogDelete()
    var clickImportant :(task : Task) -> Unit = {}
    var clickDone :(task : Task) -> Unit = {}
    var onClickDelete : (task : Task) -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("applog", "creat home")
        return inflater.inflate(R.layout.activity_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyHome = view.findViewById(R.id.recyHome)
        adapter = ListTaskAdapter(this.context)
        btnAdd = view.findViewById(R.id.btnAdd)
        imHome = view.findViewById(R.id.imNotaskHome)
        textHome = view.findViewById(R.id.textNoTaskHome)
        recyHome.apply {
            layoutManager = LinearLayoutManager(this@Fragment_home.context)
            adapter = this@Fragment_home.adapter
        }
        adapter.update(Data.listHome)
        Log.d("applog", "1")
        adapter.onClickDone = {
            adapter.isDone(it)
            Data.db.updateTask(it.id.toString(), it)
            Log.d("applog", "update data")
            if(it.isDone==1){
                clickDone(it)
                Data.listDone.add(it)
                Data.listHome.remove(it)
                if(Data.listHome.size==0){
                    Log.d("setbackground", "create")
                    imHome.setBackgroundResource(R.drawable.no_task)
                    textHome.setText("No tasks")
                }
                Log.d("applog", "add task to list done")
                Toast.makeText(context, "Đã chuyển task đến mục done", Toast.LENGTH_SHORT).show()
                deleteTask(it)
            }
        }
        adapter.onClickImportant = {
            adapter.isImportant(it)
            Data.db.updateTask(it.id.toString(), it)
            if(it.isImportant==1){
                clickImportant(it)
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

        btnAdd.setOnClickListener {
            dialogNewTask.show(childFragmentManager, null)
            dialogNewTask.clickApply = {
                Data.db.addTask(listOf(it))
                adapter.newTask(it)
                dialogNewTask.dismiss()
                imHome.setBackgroundResource(R.drawable.white)
                textHome.setText("")
            }
        }

        if(Data.listHome.size==0){
            imHome.setBackgroundResource(R.drawable.no_task)
            textHome.setText("No tasks")
        }
    }

    override fun fragmentBecameVisible() {
        Data.loadListData()
        adapter.update(Data.listHome)
    }
    fun deleteTask(task: Task){
        adapter.deleteTask(task)
    }
    fun updateTask(task:Task){
        adapter.isImportant(task)
    }
    fun newTask(task: Task){
        adapter.newTask(task)
    }
}