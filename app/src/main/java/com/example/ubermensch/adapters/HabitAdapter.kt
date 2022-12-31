package com.example.ubermensch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HabitAdapter() : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    private var pos:Int = 0
    private val habitList = ArrayList<Habit>()
    private lateinit var btn: FloatingActionButton
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.habit_layout,
            parent,false)
        return HabitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentItem = habitList?.get(position)

        if (currentItem != null && !currentItem.isDone) {
            holder.title.text = currentItem.title
            holder.note.text = currentItem.note
            holder.date.text = currentItem.date?.subSequence(0,2).toString()+"/"+currentItem.date?.subSequence(2,4)+"/"+currentItem.date?.subSequence(4,8)
            holder.difficulty.text = currentItem.difficulty
            holder.tag.text = currentItem.tag
        }
        var habitHolder = holder as HabitViewHolder
        habitHolder.itemView.findViewById<FloatingActionButton>(R.id.floatingActionButtonCheck).setTag(position)
        val habit = habitList[position]
        habitHolder.itemView.findViewById<FloatingActionButton>(R.id.floatingActionButtonCheck).setOnClickListener{
            val index = it.tag as Int
            deleteHabit(habitList,index)
            updateHabits(habitList)

        }

        /*btn = holder.itemView.findViewById(R.id.floatingActionButtonCheck)
        btn.setOnClickListener{
            deleteHabit(habitList,pos)
        }*/

    }

    fun deleteHabit(habitList: ArrayList<Habit>?, position: Int){
       this.habitList.removeAt(position)

    }
    override fun getItemCount(): Int {
        return habitList.size ?: 0
    }
    fun updateHabits(habitList: ArrayList<Habit>?){
        this.habitList. clear()
        if (habitList != null) {
            this.habitList.addAll(habitList)
        }
        notifyDataSetChanged()
    }
    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val note: TextView = itemView.findViewById(R.id.note)
        val date: TextView = itemView.findViewById(R.id.date)
        val difficulty: TextView = itemView.findViewById(R.id.difficulty)
        val tag: TextView = itemView.findViewById(R.id.tag)


    }
}


