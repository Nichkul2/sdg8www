package com.app.sdg8www

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailActivity : AppCompatActivity() {
    // View
    private lateinit var icHome: ImageView
    private lateinit var icInterview: ImageView
    private lateinit var icCommunity: ImageView
    private lateinit var icSetting: ImageView
    private lateinit var txName: TextView
    private lateinit var txDesc: TextView
    private lateinit var listView: ListView
    // Database
    private lateinit var database: FirebaseDatabase
    private lateinit var postsRef: DatabaseReference

    private var postListData: ArrayList<Post>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var id: String? = ""
        var name: String? = ""
        var description: String? = ""

        val extras = intent.extras
        if (extras != null) {
            id = extras.getString("id")
            name = extras.getString("name")
            description = extras.getString("description")
        }

        icHome = findViewById<ImageView>(R.id.ic_home)
        icInterview = findViewById<ImageView>(R.id.ic_interview)
        icCommunity = findViewById<ImageView>(R.id.ic_community)
        icSetting = findViewById<ImageView>(R.id.ic_setting)
        txName = findViewById<TextView>(R.id.tx_name)
        txDesc = findViewById<TextView>(R.id.tx_description)
        listView = findViewById(R.id.post_list_view)

        // Firebase Realtime Database 초기화
        database = FirebaseDatabase.getInstance()
        postsRef = database.getReference("Jobs").child(id.toString()).child("postList")

        txName.text = name
        txDesc.text = description

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@DetailActivity, WorkCommunityActivity::class.java)
                startActivity(intent)
                finish()
            }})

        getPostFromDatabase()

        icHome.setOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        icInterview.setOnClickListener {
            val intent = Intent(this@DetailActivity, InterviewCoachActivity::class.java)
            startActivity(intent)
            finish()
        }
        icSetting.setOnClickListener {
            val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getPostFromDatabase() {
        postsRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                postListData?.clear()  // 리스트를 새로 고침
                for (postSnapshot in dataSnapshot.children) {
                    val postData = postSnapshot.value as? Map<*, *>
                    if (postData != null) {
                        val author = postData["author"] as? String
                        val content = postData["content"] as? String
                        val date = postData["date"] as? String
                        val likeCount = postData["likeCount"] as? String
                        val post = Post(author.toString(), content.toString(), date.toString(), likeCount.toString().toInt())
                        postListData!!.add(post)
                    }
                }
                val adapterPost = PostAdapter(this@DetailActivity, postListData!!)
                listView.adapter = adapterPost
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("RealtimeDatabase", "Error getting next question", databaseError.toException())
            }
        })
    }
}