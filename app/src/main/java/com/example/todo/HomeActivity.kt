package com.example.todo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todo.databinding.ActivityHomeBinding

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
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}