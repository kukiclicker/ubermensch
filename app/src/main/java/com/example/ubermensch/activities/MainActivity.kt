package com.example.ubermensch.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.ubermensch.R
import com.example.ubermensch.databinding.ActivityMainBinding
import com.example.ubermensch.fragments.Home
import com.example.ubermensch.fragments.Pomodoro
import com.example.ubermensch.fragments.ToDoFragment
import com.example.ubermensch.fragments.User
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_animation) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_animation) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.open_animation) }
    private val fromTop: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.close_animation) }
    private lateinit var btnAddHabitOption: FloatingActionButton
    private lateinit var btnAddTodoOption: FloatingActionButton
    private lateinit var btnAddButton: FloatingActionButton
    private var selected = false
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
                R.id.to_do -> replaceFragment(ToDoFragment())
                else -> {
                }
            }
            true
        }
        btnAddButton = findViewById(R.id.floatingActionButtonAdd)
        btnAddButton.setOnClickListener {
            onAddButtonClicked()
        }

        btnAddTodoOption= findViewById(R.id.floatingActionButtonAddTodoOption)
        btnAddTodoOption.setOnClickListener{

            val intent = Intent(this,AddTodoActivity::class.java)
            startActivity(intent)
        }
        btnAddHabitOption = findViewById(R.id.floatingActionButtonAddHabitOption)
        btnAddHabitOption.setOnClickListener{

            val intent = Intent(this,AddHabitActivity::class.java)
            startActivity(intent)
        }


    }

    private fun onAddButtonClicked() {
        setVisibility(selected)
        setAnimation(selected)
        selected = !selected
    }

    private fun setAnimation(selected: Boolean) {
        if(!selected){
            btnAddHabitOption.startAnimation(fromBottom)
            btnAddTodoOption.startAnimation(fromBottom)
            btnAddButton.startAnimation(rotateOpen)
        }else{
            btnAddHabitOption.startAnimation(fromTop)
            btnAddTodoOption.startAnimation(fromTop)
            btnAddButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(selected: Boolean) {
        if(!selected){
            btnAddHabitOption.visibility = View.VISIBLE
            btnAddTodoOption.visibility = View.VISIBLE
        }else{
            btnAddHabitOption.visibility = View.INVISIBLE
            btnAddTodoOption.visibility = View.INVISIBLE
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.frames,fragment)
        fragmentTransaction.commit()
    }



}