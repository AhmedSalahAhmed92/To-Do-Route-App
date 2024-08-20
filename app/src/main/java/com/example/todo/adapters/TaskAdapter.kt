package com.example.todo.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.R
import com.example.todo.callbacks.OnTaskCheckListener
import com.example.todo.callbacks.OnTaskClickListener
import com.example.todo.callbacks.OnTaskDeleteListener
import com.example.todo.database.entity.Task
import com.example.todo.databinding.ItemTaskBinding

class TaskAdapter(private var tasksList: List<Task>) : Adapter<TaskAdapter.TaskViewHolder>() {

    var onTaskClickListener: OnTaskClickListener? = null
    var onTaskDeleteListener: OnTaskDeleteListener? = null
    var onTaskCheckListener: OnTaskCheckListener? = null

    class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskTime.text = task.time
            if (task.isDone == true) {
                binding.apply {
                    imvCheck.visibility = View.GONE
                    viewSeparator.setBackgroundResource(R.color.green)
                    tvTaskTitle.setTextColor(Color.GREEN)
                    tvCheckIsDone.visibility = View.VISIBLE
                }
            }
        }
    }

    fun updateData(tasks: List<Task>) {
        tasksList = tasks
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        tasksList = tasksList.filterIndexed { index, _ -> index != position }
        notifyItemRemoved(position)
    }

    fun checkItem(task: Task) {
        task.isDone = true
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
        holder.binding.imvCheck.setOnClickListener {
            onTaskCheckListener?.onTaskCheck(task, position)
        }
        holder.binding.layoutDelete.setOnClickListener {
            onTaskDeleteListener?.onTaskDelete(task, position)
        }
    }
}