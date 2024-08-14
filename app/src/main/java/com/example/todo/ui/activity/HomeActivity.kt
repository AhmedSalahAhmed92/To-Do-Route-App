package com.example.todo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.fragment.AddTaskBottomSheet
import com.example.todo.ui.fragment.TodoTasksFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var tasksListFragment : TodoTasksFragment
    //private val settingsFragment = TodoSettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intFragment()
        bottomNavigation()
    }

    private fun intFragment() {
        tasksListFragment = TodoTasksFragment()
    }

    private fun bottomNavigation() {
        binding.apply {
            bottomNavigation.apply {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_tasks_list -> showFragment(tasksListFragment)

                        R.id.nav_settings -> {}
                    }
                    return@setOnItemSelectedListener true
                }
                selectedItemId = R.id.nav_tasks_list
            }
        }
        binding.fabAddTask.setOnClickListener {
            val bottomSheet = AddTaskBottomSheet()
                .show(supportFragmentManager, null)
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}