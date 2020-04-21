package com.nmai.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ListTaskAdapter(
    val context: Context?,
    var onClickDone : (task : Task) -> Unit = {},
    var onClickImportant :(task : Task) -> Unit = {},
    var longClick :(task:Task) -> Unit = {}
) : RecyclerView.Adapter<ListTaskAdapter.TaskHolder>() {
    var listTask = mutableListOf<Task>()
    inner class TaskHolder(val view : View) : RecyclerView.ViewHolder(view){
        val btnDone : ImageButton
        val btnImportant : ImageButton
        val textContent : TextView
        val image : ImageView
        init{
            btnDone = view.findViewById(R.id.btnDone)
            btnImportant = view.findViewById(R.id.btnImportant)
            textContent = view.findViewById(R.id.textContent)
            image = view.findViewById(R.id.color)
        }
        fun bind(task: Task){
            textContent.text = task.content
            if(task.isImportant==1){
                btnImportant.setBackgroundResource(R.drawable.star_true)
            } else btnImportant.setBackgroundResource(R.drawable.star_false)
            if(task.isDone==1) btnDone.setBackgroundResource(R.drawable.done_true)
            else btnDone.setBackgroundResource(R.drawable.done_false)
            if(task.isDone==1) image.setBackgroundResource(R.drawable.green)
            else if(task.isImportant==1) image.setBackgroundResource(R.drawable.yellow)
            else image.setBackgroundResource(R.drawable.red)
            btnDone.setOnClickListener {
                onClickDone(task)
            }
            btnImportant.setOnClickListener {
                onClickImportant(task)
            }
            itemView.setOnLongClickListener{
               longClick(task)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(listTask[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task, parent, false)
        return TaskHolder(view)
    }
    fun update(list : List<Task>){
        listTask.clear()
        listTask.addAll(list)
        notifyDataSetChanged()
    }
    fun isDone(task : Task){
        if(task.isDone==1) task.isDone = 0
        else task.isDone = 1
        notifyItemChanged(listTask.indexOf(task))
    }
    fun isImportant(task: Task){
        if(task.isImportant==1) task.isImportant = 0
        else task.isImportant = 1
        notifyItemChanged(listTask.indexOf(task))
    }
    fun newTask(task: Task){
        if(!listTask.contains(task)){
            listTask.add(task)
            notifyItemChanged(listTask.size-1)
        }
    }
    fun deleteTask(task: Task){
        listTask.remove(task)
        notifyDataSetChanged()
    }

}