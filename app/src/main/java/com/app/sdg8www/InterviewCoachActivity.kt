package com.app.sdg8www

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log

class InterviewCoachActivity : AppCompatActivity() {

    private var listData: ArrayList<String>? = null
    private var currentIndex = 0
    private lateinit var database: DatabaseReference
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_coach)

        // Get the logged-in user's email
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userEmail = user.email ?: "unknown_user"
        } else {
            Log.e("InterviewCoachActivity", "No user is logged in.")
            finish()  // Exit the activity if no user is logged in
            return
        }

        // Initialize the Firebase database reference with the user's email
        database = Firebase.database.reference.child("Interview").child(userEmail.replace(".", ","))

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alert = AlertDialog.Builder(this@InterviewCoachActivity)
                alert.setTitle("Please confirm to exit")
                alert.setPositiveButton("Exit") { dialogInterface: DialogInterface?, i: Int -> finish() }
                alert.setNegativeButton("Cancel") { dialogInterface: DialogInterface?, i: Int ->
                    dialogInterface?.dismiss()
                }
                alert.show()
            }
        })

        listData = ArrayList()
        listData!!.apply {
            add("Test chat bot")
            add("Welcome to job interview")
            add("Hello")
            add("test for chat bot")
            add("ทดสอบการใช้งาน")
            add("รบกวนสอบถามเพิ่มเติม")
            add("ค้นหางานที่ต้องการ")
            add("Serch your job")
            add("coach for your job")
            add("test me!!")
            add("random test")
            add("สอบถามรายละเอียด")
            add("new message")
            add("ทดสอบระบบ")
            add("sdg8www application")
            add("test my app")
            add("Welcome to job interview")
        }

        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)
        val result = findViewById<TextView>(R.id.tx_result)
        val header = findViewById<TextView>(R.id.tx_header)
        val etType = findViewById<EditText>(R.id.et_type)
        val layout = findViewById<LinearLayout>(R.id.layout_list)
        val btnSend = findViewById<Button>(R.id.btn_send)

        btnSend.setOnClickListener {
            if (etType.text.isNotEmpty()) {
                val userMessage = etType.text.toString()
                val textViewUser = TextView(this)
                textViewUser.gravity = Gravity.RIGHT
                textViewUser.setTextColor(Color.parseColor("#ffffff"))
                textViewUser.text = userMessage
                textViewUser.setBackgroundResource(R.drawable.background_circle_gray)
                textViewUser.setPadding(20, 20, 20, 20)
                layout.addView(textViewUser)

                val botResponse = if (currentIndex < listData!!.size) {
                    listData!![currentIndex++]
                } else {
                    "no more questions!"
                }
                val textViewBot = TextView(this)
                textViewBot.gravity = Gravity.LEFT
                textViewBot.setTextColor(Color.parseColor("#ffffff"))
                textViewBot.text = botResponse
                textViewBot.setBackgroundResource(R.drawable.background_circle_result)
                textViewBot.setPadding(20, 20, 20, 20)
                layout.addView(textViewBot)

                // Append conversation entry to Firebase
                val conversationEntry = ConversationEntry(userMessage, botResponse)
                database.push().setValue(conversationEntry)

                etType.setText("")
            }
        }

        result.setOnClickListener {
            val intent = Intent(this@InterviewCoachActivity, ResultActivity::class.java)
            startActivity(intent)
            finish()
        }
        icHome.setOnClickListener {
            val intent = Intent(this@InterviewCoachActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        icCommunity.setOnClickListener {
            val intent = Intent(this@InterviewCoachActivity, WorkCommunityActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@InterviewCoachActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val interviewPaint = header.paint
        val interviewWidth = interviewPaint.measureText(header.text.toString())
        val interviewTextShader: Shader = LinearGradient(
            0f, 0f, interviewWidth, header.textSize,
            intArrayOf(
                Color.parseColor("#915B5B"),
                Color.parseColor("#F79A9A")
            ), null, Shader.TileMode.REPEAT
        )
        header.paint.shader = interviewTextShader
    }

    data class ConversationEntry(val userMessage: String, val botResponse: String)
}

