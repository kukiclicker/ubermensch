package com.example.ubermensch.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ubermensch.R
import com.example.ubermensch.databinding.ActivityMainBinding
import com.example.ubermensch.fragments.Home
import com.example.ubermensch.fragments.Pomodoro
import com.example.ubermensch.fragments.ToDo
import com.example.ubermensch.fragments.User
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.home -> replaceFragment(Home())
                R.id.pomodoro -> replaceFragment(Pomodoro())
                R.id.user -> replaceFragment(User())
                R.id.to_do -> replaceFragment(ToDo())
                else -> {
                }
            }
            true
        }
        val btnAddHabit: FloatingActionButton = findViewById(R.id.floatingActionButtonAdd)
        btnAddHabit.setOnClickListener{
            val intent = Intent(this,AddHabitActivity::class.java)
            startActivity(intent)
        }

    }

    private fun replaceFragment(fragment : Fragment){

        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.frames,fragment)
        fragmentTransaction.commit()


    }
}