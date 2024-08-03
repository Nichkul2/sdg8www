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
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WorkCommunityActivity : AppCompatActivity() {
    var listName: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alert = AlertDialog.Builder(this@WorkCommunityActivity)
                alert.setTitle("Please confirm to exit")
                alert.setPositiveButton("Exit") { dialogInterface: DialogInterface?, i: Int -> finish() }
                alert.setNegativeButton("Cancel") { dialogInterface: DialogInterface?, i: Int ->
                    dialogInterface?.dismiss()
                }
                alert.show()
            }})
        setContentView(R.layout.activity_work_community)
        val icHome = findViewById<ImageView>(R.id.ic_home)
        val icInterview = findViewById<ImageView>(R.id.ic_interview)
        val icCommunity = findViewById<ImageView>(R.id.ic_community)
        val icSetting = findViewById<ImageView>(R.id.ic_setting)
        val searchView = findViewById<SearchView>(R.id.et_search)

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
        listName = ArrayList()
        listName!!.add("Teacher/Educator")
        listName!!.add("Business Analyst")
        listName!!.add("Software Developer")
        listName!!.add("Human Resources Manager")
        listName!!.add("Sustainability Consultant")
        listName!!.add("Social Worker")
        val adapterItem = ItemAdapter(this@WorkCommunityActivity, listName!!)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.setAdapter(adapterItem)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (listName!!.contains(query)) {
                    adapterItem.filter.filter(query)
                    listView.setAdapter(adapterItem)
                } else {
                    Toast.makeText(this@WorkCommunityActivity, "No Job found..", Toast.LENGTH_LONG)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterItem.filter.filter(newText)
                listView.setAdapter(adapterItem)
                return false
            }
        })
    }
}
