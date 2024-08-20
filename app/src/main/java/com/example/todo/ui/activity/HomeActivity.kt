package com.example.todo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.callbacks.OnTaskAddedListener
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.fragment.AddTaskBottomSheet
import com.example.todo.ui.fragment.TodoSettingsFragment
import com.example.todo.ui.fragment.TodoTasksFragment

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tasksListFragment: TodoTasksFragment
    private lateinit var settingsFragment: TodoSettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
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
                            binding.toolbarTitle.setText(R.string.to_do_list)
                        }

                        R.id.nav_settings -> {
                            showFragment(settingsFragment)
                            binding.toolbarTitle.setText(R.string.settings)
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
            if (settingsFragment.isVisible) return@setOnClickListener
            val bottomSheet = AddTaskBottomSheet()
            bottomSheet.onAddedTaskListener = onTaskAddedListener()
            bottomSheet.show(supportFragmentManager, null)
        }
    }

    private fun onTaskAddedListener() = object : OnTaskAddedListener {
        override fun onTaskAdded() {
            if (tasksListFragment.isVisible)
                if (tasksListFragment.selectedDate == null)
                    tasksListFragment.getAllTasksFromDataBase()
                else
                    tasksListFragment.getUpdatedTaskList(tasksListFragment.calendar.time)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bottomNavigation.setOnItemSelectedListener(null)
        binding.fabAddTask.setOnClickListener(null)
        _binding = null
    }

}