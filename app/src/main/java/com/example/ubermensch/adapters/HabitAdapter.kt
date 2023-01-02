package com.example.ubermensch.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HabitAdapter() : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    private val habitList = ArrayList<Habit>()
    private lateinit var btn: FloatingActionButton
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
        holder.date.text = currentItem.date?.subSequence(0,2).toString()+"/"+currentItem.date?.subSequence(2,4)+"/"+currentItem.date?.subSequence(4,8)
        holder.difficulty.text = currentItem.difficulty
        holder.tag.text = currentItem.tag

        btn = holder.itemView.findViewById(R.id.floatingActionButtonCheck)
        try {
            btn.setOnClickListener{

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
        } catch (e: Exception) {

        }
    }
    override fun getItemCount(): Int {
        return habitList.size ?: 0
    }
    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val note: TextView = itemView.findViewById(R.id.note)
        val date: TextView = itemView.findViewById(R.id.date)
        val difficulty: TextView = itemView.findViewById(R.id.difficulty)
        val tag: TextView = itemView.findViewById(R.id.tag)
    }
}


