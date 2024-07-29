package com.app.sdg8www

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@ResultActivity, InterviewCoachActivity::class.java)
                startActivity(intent)
                finish()
            }})
        val txHeader = findViewById<TextView>(R.id.tx_header)
        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)

        icHome.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        icCommunity.setOnClickListener {
            val intent = Intent(this@ResultActivity, WorkCommunityActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@ResultActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val interviewpaint = txHeader.paint
        val interviewwidth = interviewpaint.measureText(txHeader.text.toString())
        val interviewtextShader: Shader = LinearGradient(0f, 0f, interviewwidth, txHeader.textSize, intArrayOf(
            Color.parseColor("#915B5B"),
            Color.parseColor("#F79A9A")
        ), null, Shader.TileMode.REPEAT)

    }
}