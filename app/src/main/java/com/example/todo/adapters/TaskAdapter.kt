package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.callbacks.OnTaskClickListener
import com.example.todo.database.entity.Task
import com.example.todo.databinding.ItemTaskBinding

class TaskAdapter(private var tasksList: List<Task>) : Adapter<TaskAdapter.TaskViewHolder>() {

    var onTaskClickListener: OnTaskClickListener? = null

    class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskTime.text = task.time
        }

        fun setOnClickListener(onClickListener: View.OnClickListener) {
            binding.root.setOnClickListener(onClickListener)
        }
    }

    fun updateData(tasks: List<Task>) {
        tasksList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList[position]
        holder.bind(task)
        holder.binding.constraintLayoutTask.setOnClickListener {
            onTaskClickListener?.onTaskClick(task, position)
        }
    }
}