package com.app.sdg8www

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WorkCommunityActivity : AppCompatActivity() {
    var listData: ArrayList<Item>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alert = AlertDialog.Builder(this@WorkCommunityActivity)
                alert.setTitle("ต้องการออกจากแอพพลิเคชั่นหรือไม่?")
                alert.setPositiveButton("ออก") { dialogInterface: DialogInterface?, i: Int -> finish() }
                alert.setNegativeButton("ยกเลิก") { dialogInterface: DialogInterface?, i: Int ->
                    dialogInterface?.dismiss()
                }
                alert.show()
            }})
        setContentView(R.layout.activity_work_community)
        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)

        icHome.setOnClickListener {
            val intent = Intent(this@WorkCommunityActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        icInterview.setOnClickListener {
            val intent = Intent(this@WorkCommunityActivity, InterviewCoachActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@WorkCommunityActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
        val header = findViewById<TextView>(R.id.tx_header)
        val paint = header.paint
        val width = paint.measureText(header.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, header.textSize, intArrayOf(
            Color.parseColor("#9C9AF7"),
            Color.parseColor("#9391E9"),
            Color.parseColor("#5C5B91")
        ), null, Shader.TileMode.REPEAT)

        header.paint.setShader(textShader)
        listData = ArrayList()
        listData!!.add(
            Item("1.","Teacher/Educator","Provide education and training to develop a skilled workforce that contributes to economic growth and sustainable development"))
        listData!!.add(
            Item("2.","Business Analyst","Analyze business processes and recommend improvements to drive economic growth and job creation"))
        listData!!.add(
            Item("3.","Software Developer","Design, develop, and maintain software applications that enhance business productivity and promote economic growth"))
        listData!!.add(
            Item("4.","Human Resources Manager","Oversee recruitment and employee development programs to ensure fair and productive employment practices"))
        listData!!.add(
            Item("5.","Sustainability Consultant","Advise organizations on sustainable practices that promote economic growth while minimizing environmental impact"))
        listData!!.add(
            Item("6.","Social Worker","Support individuals and communities in overcoming barriers to employment and economic participation"))

        val adapterItem = ItemAdapter(this@WorkCommunityActivity, listData!!)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.setAdapter(adapterItem)
    }
}