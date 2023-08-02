package com.example.ubermensch.DataLayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.DataLayer.models.Habit
import com.example.ubermensch.DataLayer.repositories.ExperienceRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HabitAdapter() : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    private val habitList = ArrayList<Habit>()
    private lateinit var btnDelete:ImageView
    private lateinit var btnCheck:FloatingActionButton
    private lateinit var btnRefresh:ImageView

    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Habits").child(
            FirebaseAuth.getInstance().currentUser?.uid
            ?: "Error! UID ")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.habit_layout,
            parent,false)
        return HabitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentItem = habitList.get(position)
        holder.title.text = currentItem.title
        holder.note.text = currentItem.note
        holder.date.text = currentItem.date
        holder.difficulty.text = currentItem.difficulty
        holder.tag.text = currentItem.tag
        holder.counter.text = currentItem.counter.toString()
        btnDelete = holder.itemView.findViewById(R.id.imgDelete)
        btnCheck = holder.itemView.findViewById(R.id.floatingActionButtonCheck)
        btnRefresh = holder.itemView.findViewById(R.id.imgRefresh)


        try {
            btnDelete.setOnClickListener{
                val query = databaseReference.orderByChild("title").equalTo(currentItem.title.toString())
                query.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnapshot in snapshot.children) {
                            val item = itemSnapshot.getValue(Habit::class.java)
                            if (item != null) {
                                itemSnapshot.ref.removeValue()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

            btnCheck.setOnClickListener {
                val query =
                    databaseReference.orderByChild("title").equalTo(currentItem.title.toString())
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnapshot in snapshot.children) {
                            val item = itemSnapshot.getValue(Habit::class.java)
                            if (item != null) {
                                itemSnapshot.child("counter").ref.setValue(
                                    itemSnapshot.child("counter").getValue().toString().toInt() + 1
                                )
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                var xp:Double = 200.0
                ExperienceRepository.updateXP(xp)



            }
                btnRefresh.setOnClickListener{

                    val secondQuery = databaseReference.orderByChild("title").equalTo(currentItem.title.toString())
                    secondQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (itemSnapshot in snapshot.children) {
                                val item = itemSnapshot.getValue(Habit::class.java)
                                if (item != null) {

                                    itemSnapshot.child("counter").ref.setValue(0)

                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                }


        } catch (e: Exception) {

        }
    }
    override fun getItemCount(): Int {
        return habitList.size
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
        val counter: TextView = itemView.findViewById(R.id.countStreak)

    }
}


