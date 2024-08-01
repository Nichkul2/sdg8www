package com.app.sdg8www

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val usernameEditText = findViewById<EditText>(R.id.et_username)
        val emailEditText = findViewById<EditText>(R.id.et_email)
        val phoneNumberEditText = findViewById<EditText>(R.id.et_phone_number)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val confirmPasswordEditText = findViewById<EditText>(R.id.et_confirm_passowrd)
        val registerButton = findViewById<Button>(R.id.btn_register)
        val signIn = findViewById<TextView>(R.id.tx_sign_in)
        registerButton.setOnClickListener {
            if (usernameEditText.text.isEmpty()
                || emailEditText.text.isEmpty()
                || phoneNumberEditText.text.isEmpty()
                || passwordEditText.text.isEmpty()
                || confirmPasswordEditText.text.isEmpty()
            ) {
                val alert = AlertDialog.Builder(this@RegisterActivity)
                alert.setTitle("Please enter your information!")
                alert.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    View.OnClickListener {
                        dialog.dismiss()
                    }
                }
                alert.show()
            } else if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()){
                val alert = AlertDialog.Builder(this@RegisterActivity)
                alert.setTitle("password หรือ confirm password ไม่ถูกต้อง!")
                alert.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    View.OnClickListener {
                        dialog.dismiss()
                    }
                }
                alert.show()
        } else {
                auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.uid
                            database =
                                FirebaseDatabase.getInstance("https://sdg8-460da-default-rtdb.firebaseio.com/")
                            reference = database!!.getReference("Users")
                            val userProfile = User("",usernameEditText.text.toString(),phoneNumberEditText.text.toString())
                            reference!!.child(user?.uid.toString()).setValue(userProfile)
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            //updateUI(null)
                        }
                    }

            }
        }
        signIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}