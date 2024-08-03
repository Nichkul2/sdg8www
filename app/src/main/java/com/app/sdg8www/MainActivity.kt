package com.app.sdg8www

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.sdg8www.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    val alert = AlertDialog.Builder(this@MainActivity)
                    alert.setTitle("Please confirm to exit")
                    alert.setPositiveButton("Exit") { dialogInterface: DialogInterface?, i: Int -> finish() }
                    alert.setNegativeButton("Cancel") { dialogInterface: DialogInterface?, i: Int ->
                        dialogInterface?.dismiss()
                    }
                    alert.show()
        }})
        val layoutInterviewCoach = findViewById<LinearLayout>(R.id.layout_interview_coach)
        val layoutWorkCommunity = findViewById<LinearLayout>(R.id.layout_work_community)
        val userFeedBack = findViewById<TextView>(R.id.tx_feed_back)
        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)

        userFeedBack.setOnClickListener {
            val intent = Intent(this@MainActivity, FeedbackActivity::class.java)
            startActivity(intent)
            finish()
        }
        layoutInterviewCoach.setOnClickListener {
            val intent = Intent(this@MainActivity, MainAiActivity::class.java)
            startActivity(intent)
            finish()
        }
        icInterview.setOnClickListener {
            val intent = Intent(this@MainActivity, MainAiActivity::class.java)
            startActivity(intent)
            finish()
        }
        layoutWorkCommunity.setOnClickListener {
            val intent = Intent(this@MainActivity, WorkCommunityActivity::class.java)
            startActivity(intent)
            finish()
        }
        icCommunity.setOnClickListener {
            val intent = Intent(this@MainActivity, WorkCommunityActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
        val interview = findViewById<TextView>(R.id.tx_interview_coach)
        val interviewpaint = interview.paint
        val interviewwidth = interviewpaint.measureText(interview.text.toString())
        val interviewtextShader: Shader = LinearGradient(0f, 0f, interviewwidth, interview.textSize, intArrayOf(
            Color.parseColor("#915B5B"),
            Color.parseColor("#F79A9A")
        ), null, Shader.TileMode.REPEAT)

        val workCommu = findViewById<TextView>(R.id.tx_work_community)
        val paint = workCommu.paint
        val width = paint.measureText(workCommu.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, workCommu.textSize, intArrayOf(
            Color.parseColor("#9C9AF7"),
            Color.parseColor("#9391E9"),
            Color.parseColor("#5C5B91")
        ), null, Shader.TileMode.REPEAT)

    }
}
