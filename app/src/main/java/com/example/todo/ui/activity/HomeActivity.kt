package com.example.todo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.callback.OnTaskAddedListener
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.fragment.AddTaskBottomSheet
import com.example.todo.ui.fragment.TodoSettingsFragment
import com.example.todo.ui.fragment.TodoTasksFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var tasksListFragment: TodoTasksFragment
    private lateinit var settingsFragment: TodoSettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intFragment()
        bottomNavigation()
    }

    private fun intFragment() {
        tasksListFragment = TodoTasksFragment()
        settingsFragment = TodoSettingsFragment()
    }

    private fun bottomNavigation() {
        binding.apply {
            bottomNavigation.apply {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_tasks_list -> {
                            showFragment(tasksListFragment)
                            binding.title.setText(R.string.to_do_list)
                        }

                        R.id.nav_settings -> {
                            showFragment(settingsFragment)
                            binding.title.setText(R.string.settings)
                        }
                    }
                    return@setOnItemSelectedListener true
                }
                selectedItemId = R.id.nav_tasks_list
            }
        }
        onAddTaskFabClick()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun onAddTaskFabClick() {
        binding.fabAddTask.setOnClickListener {
            val bottomSheet = AddTaskBottomSheet()
            bottomSheet.onAddedTaskListener = onTaskAddedListener()
            bottomSheet.show(supportFragmentManager, "add_task_bottom_sheet")
        }
    }

    private fun onTaskAddedListener() = object : OnTaskAddedListener {
        override fun onTaskAdded() {
            if (tasksListFragment.isVisible) tasksListFragment.getAllTasksFromDataBase()
        }
    }
}