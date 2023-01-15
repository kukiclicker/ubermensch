package com.example.ubermensch.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HabitAdapter() : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    private val habitList = ArrayList<Habit>()
    private lateinit var delete:ImageView
    private lateinit var btn:FloatingActionButton
    private lateinit var refresh:ImageView


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
        delete = holder.itemView.findViewById(R.id.imgDelete)
        btn = holder.itemView.findViewById(R.id.floatingActionButtonCheck)
        refresh = holder.itemView.findViewById(R.id.imgRefresh)


        try {
            delete.setOnClickListener{
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

            /*edit.setOnClickListener{
                val title = holder.title.text.toString()
                val note = holder.note.text.toString()
                val tag = holder.tag.text.toString()
                val difficulty = holder.difficulty.text.toString()
                val date = holder.date.text.toString()
                val counter = holder.counter.text.toString().toInt()

                val editMap = mapOf(
                    "title" to title,
                    "note" to note,
                    "tag" to tag,
                    "difficulty" to difficulty,
                    "date" to date,
                    "counter" to counter
                )
                val query = databaseReference.orderByChild("title").equalTo(currentItem.title.toString())
                query.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnapshot in snapshot.children) {
                            val item = itemSnapshot.getValue(Habit::class.java)
                            if (item != null) {
                                itemSnapshot.ref.updateChildren(editMap)
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }*/
            btn.setOnClickListener {
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
            }
                refresh.setOnClickListener{

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
                    /*refresh.animate()
                        .rotation(360f)
                        .setDuration(5000)
                        .start()

                     */
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


