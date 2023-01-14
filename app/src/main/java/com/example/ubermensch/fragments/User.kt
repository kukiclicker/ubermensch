package com.example.ubermensch.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.ubermensch.R
import com.example.ubermensch.activities.ChangePasswordActivity
import com.example.ubermensch.activities.LogInActivity
import com.google.firebase.auth.FirebaseAuth



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [User.newInstance] factory method to
 * create an instance of this fragment.
 */
class User : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val user = FirebaseAuth.getInstance()
    private lateinit var displayName:TextView
    private lateinit var progress:ProgressBar
    private lateinit var level:TextView
    private lateinit var btnLogout: Button
    private lateinit var btnChangePass:Button

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
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayName = view.findViewById(R.id.username)
        progress = view.findViewById(R.id.userLevelProgress)
        level = view.findViewById(R.id.userLevel)
        btnLogout = view.findViewById(R.id.buttonLogout)
        btnChangePass = view.findViewById(R.id.buttonChangePassword)
        if(user != null)
        {
            displayName.text = user.currentUser?.email.toString().subSequence(0,user.currentUser?.email.toString().indexOf("@"))
        }
        btnLogout.setOnClickListener{
            user.signOut()
            startActivity(Intent(activity,LogInActivity::class.java))
        }
        btnChangePass.setOnClickListener {
            startActivity(Intent(activity,ChangePasswordActivity::class.java))
            activity?.finish()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment User.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            User().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}