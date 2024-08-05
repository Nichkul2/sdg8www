package com.app.sdg8www

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class FeedbackActivity : AppCompatActivity() {
    var point: Int? = null
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

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
        val txDone = findViewById<TextView>(R.id.tx_done)
        val star1 = findViewById<ImageView>(R.id.star_1)
        val star2 = findViewById<ImageView>(R.id.star_2)
        val star3 = findViewById<ImageView>(R.id.star_3)
        val star4 = findViewById<ImageView>(R.id.star_4)
        val star5 = findViewById<ImageView>(R.id.star_5)
        val starbg1 = findViewById<ImageView>(R.id.star_bg1)
        val starbg2 = findViewById<ImageView>(R.id.star_bg2)
        val starbg3 = findViewById<ImageView>(R.id.star_bg3)
        val starbg4 = findViewById<ImageView>(R.id.star_bg4)
        val starbg5 = findViewById<ImageView>(R.id.star_bg5)
        val etComment = findViewById<EditText>(R.id.et_comment)
        starbg1.setOnClickListener {
            point = 1
            star1.visibility = View.VISIBLE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        starbg2.setOnClickListener {
            point = 2
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        starbg3.setOnClickListener {
            point = 3
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        starbg4.setOnClickListener {
            point = 4
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.GONE
        }
        starbg5.setOnClickListener {
            point = 5
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.VISIBLE
        }
        star1.setOnClickListener {
            point = 1
            star1.visibility = View.VISIBLE
            star2.visibility = View.GONE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        star2.setOnClickListener {
            point = 2
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.GONE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        star3.setOnClickListener {
            point = 3
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.GONE
            star5.visibility = View.GONE
        }
        star4.setOnClickListener {
            point = 4
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.GONE
        }
        star5.setOnClickListener {
            point = 5
            star1.visibility = View.VISIBLE
            star2.visibility = View.VISIBLE
            star3.visibility = View.VISIBLE
            star4.visibility = View.VISIBLE
            star5.visibility = View.VISIBLE
        }
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
            if (userEmail != null) {
                database = FirebaseDatabase.getInstance("https://sdg8-460da-default-rtdb.firebaseio.com")
                reference = database!!.getReference("Rate")

                val rate = Rate(userEmail, etComment.text.toString(), point!!)
                reference!!.push().setValue(rate)

                layout1.visibility = View.GONE
                layout2.visibility = View.GONE
                layout3.visibility = View.VISIBLE
            } else {
                // Handle the case where email is null
            }
        }
        txDone.setOnClickListener {
            val intent = Intent(this@FeedbackActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
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
