package com.example.ubermensch.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.ubermensch.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Pomodoro.newInstance] factory method to
 * create an instance of this fragment.
 */
class Pomodoro : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var timer: CountDownTimer
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var timerText:TextView
    private lateinit var btnSetTimer: Button
    private lateinit var setTime:TimePicker
    private lateinit var btnConfirm: Button
    private lateinit var btnPause: Button
    private lateinit var timerIcon: ImageView
    private var remainingMS:Long = 0
    private var timeMinutes:Int = 0
    private var timeHours:Int = 0
    private var timeInMS:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_pomodoro, container, false)
    }
    override fun onStart() {
        super.onStart()
        var show = false
        if(!timerText.text.equals("00:00:00")) {
            show = true
        }
        timer = object: CountDownTimer(timeInMS,1) {
            @SuppressLint("SetTextI18n")
            override fun onTick(remaining: Long) {
                remainingMS =remaining
                val seconds = remaining/1000%60
                val minutes = remaining/60000%60
                val hours = remaining/3600000%60

                timerText.text = if(hours<10)"0"+hours.toString()
                else{hours.toString()}+":"+
                        if(minutes<10)"0"+minutes.toString()+":"
                        else{minutes.toString()+":"}+
                        if(seconds<10)"0"+seconds.toString()
                        else{seconds.toString()}
            }
            override fun onFinish() {
                if(show)
                {
                    Toast.makeText( activity,"Time is up! Take a break!", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
    override fun onStop() {
        super.onStop()
        timeInMS = 0
        timerText.text = "00:00:00"

    }
    override fun onPause() {
        super.onPause()
        timer.cancel()
        timeInMS = remainingMS

    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStart = view.findViewById(R.id.btnStart)
        btnStop = view.findViewById(R.id.btnStop)
        btnPause = view.findViewById(R.id.btnPause)
        timerText = view.findViewById(R.id.timer)
        btnSetTimer = view.findViewById(R.id.btnSetTime)
        setTime = view.findViewById(R.id.setTime)
        btnConfirm = view.findViewById(R.id.btnConfirm)
        timerIcon = view.findViewById(R.id.ic_timer)

        setTime.setIs24HourView(true)

        btnStart.setOnClickListener{
            onStart()
        }
        btnStop.setOnClickListener{
            onStop()
        }
        btnPause.setOnClickListener{
            onPause()

        }
        timerText.setOnClickListener{
            timerText.visibility = View.INVISIBLE
            btnSetTimer.visibility = View.INVISIBLE
            setTime.visibility = View.VISIBLE
            btnStart.visibility = View.INVISIBLE
            btnStop.visibility = View.INVISIBLE
            timerIcon.visibility = View.INVISIBLE
            btnConfirm.visibility = View.VISIBLE
            timerText.text =""
            btnPause.visibility = View.INVISIBLE

        }
        btnConfirm.setOnClickListener{

            timerText.visibility = View.VISIBLE
            setTime.visibility = View.INVISIBLE
            btnStart.visibility = View.VISIBLE
            btnStop.visibility = View.VISIBLE
            timerIcon.visibility = View.VISIBLE
            btnConfirm.visibility = View.INVISIBLE
            btnPause.visibility = View.VISIBLE
            timeMinutes = setTime.minute
            timeHours = setTime.hour
            timerText.text = if(timeHours<10)"0"+timeHours.toString()
            else{timeHours.toString()}+":"+
                    if(timeMinutes<10)"0"+timeMinutes.toString()
                    else{timeMinutes.toString()}+":00"
            timeInMS = (timeMinutes*60000 + timeHours*3600000).toLong()

        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Pomodoro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Pomodoro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}