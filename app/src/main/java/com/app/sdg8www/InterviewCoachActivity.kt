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

class InterviewCoachActivity : AppCompatActivity() {
    var listData: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_coach)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alert = AlertDialog.Builder(this@InterviewCoachActivity)
                alert.setTitle("ต้องการออกจากแอพพลิเคชั่นหรือไม่?")
                alert.setPositiveButton("ออก") { dialogInterface: DialogInterface?, i: Int -> finish() }
                alert.setNegativeButton("ยกเลิก") { dialogInterface: DialogInterface?, i: Int ->
                    dialogInterface?.dismiss()
                }
                alert.show()
            }})
        listData = ArrayList()
        listData!!.add("ทดสอบ chat bot")
        listData!!.add("Welcome to job interview")
        listData!!.add("Hello")
        listData!!.add("test for chat bot")
        listData!!.add("ทดสอบการใช้งาน")
        listData!!.add("รบกวนสอบถามเพิ่มเติม")
        listData!!.add("ค้นหางานที่ต้องการ")
        listData!!.add("Serch your job")
        listData!!.add("coach for your job")
        listData!!.add("test me!!")
        listData!!.add("random test")
        listData!!.add("สอบถามรายละเอียด")
        listData!!.add("new message")
        listData!!.add("ทดสอบระบบ")
        listData!!.add("sdg8www application")
        listData!!.add("test my app")
        listData!!.add("Welcome to job interview")

        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)
        val result = findViewById<TextView>(R.id.tx_result)
        val header = findViewById<TextView>(R.id.tx_header)
        val etType = findViewById<EditText>(R.id.et_type)
        val layout = findViewById<LinearLayout>(R.id.layout_list)
        val btnSend = findViewById<Button>(R.id.btn_send)
        btnSend.setOnClickListener{
            if (etType.text.isNotEmpty()){
                val textview = TextView(this)
                textview.setGravity(Gravity.RIGHT)
                textview.setTextColor(Color.parseColor("#ffffff"))
                textview.text = etType.text
                textview.setBackgroundResource(R.drawable.background_circle_gray)
                val param = textview.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(10,10,10,10)
                textview.setPadding(20,20,20,20)
                layout.addView(textview)
                val rnds = (0..16).random()
                val textview2 = TextView(this)
                textview2.setGravity(Gravity.LEFT)
                textview2.setTextColor(Color.parseColor("#ffffff"))
                textview2.text = listData!![rnds]
                textview2.setBackgroundResource(R.drawable.background_circle_result)
                val param2 = textview2.layoutParams as ViewGroup.MarginLayoutParams
                param2.setMargins(10,10,10,10)
                textview.setPadding(20,20,20,20)
                layout.addView(textview2)
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
        val interviewpaint = header.paint
        val interviewwidth = interviewpaint.measureText(header.text.toString())
        val interviewtextShader: Shader = LinearGradient(0f, 0f, interviewwidth, header.textSize, intArrayOf(
            Color.parseColor("#915B5B"),
            Color.parseColor("#F79A9A")
        ), null, Shader.TileMode.REPEAT)

    }
}