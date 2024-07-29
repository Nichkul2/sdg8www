package com.app.sdg8www

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@FeedbackActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }})
        val tx1 = findViewById<TextView>(R.id.tx_1)
        val tx2 = findViewById<TextView>(R.id.tx_2)
        val tx3 = findViewById<TextView>(R.id.tx_3)
        val txSelect = findViewById<TextView>(R.id.tx_select)
        val txSubmit = findViewById<TextView>(R.id.tx_submit)
        val layout1 = findViewById<LinearLayout>(R.id.layout_1)
        val layout2 = findViewById<LinearLayout>(R.id.layout_2)
        val layout3 = findViewById<LinearLayout>(R.id.layout_3)
        layout1.visibility = View.VISIBLE
        layout2.visibility = View.GONE
        layout3.visibility = View.GONE
        tx1.setOnClickListener {
            txSelect.text = tx1.text
            layout1.visibility = View.GONE
            layout2.visibility = View.VISIBLE
            layout3.visibility = View.GONE
        }
        tx2.setOnClickListener {
            txSelect.text = tx2.text
            layout1.visibility = View.GONE
            layout2.visibility = View.VISIBLE
            layout3.visibility = View.GONE
        }
        tx3.setOnClickListener {
            txSelect.text = tx3.text
            layout1.visibility = View.GONE
            layout2.visibility = View.VISIBLE
            layout3.visibility = View.GONE
        }
        txSubmit.setOnClickListener {
            layout1.visibility = View.GONE
            layout2.visibility = View.GONE
            layout3.visibility = View.VISIBLE
        }
        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)

        icCommunity.setOnClickListener {
            val intent = Intent(this@FeedbackActivity, WorkCommunityActivity::class.java)
            startActivity(intent)
            finish()
        }
        icInterview.setOnClickListener {
            val intent = Intent(this@FeedbackActivity, InterviewCoachActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@FeedbackActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}