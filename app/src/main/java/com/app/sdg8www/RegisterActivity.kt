package com.app.sdg8www

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var fileUri:Uri? = null
    var uuid:String = ""
    var imageView:ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val usernameEditText = findViewById<EditText>(R.id.et_username)
        val emailEditText = findViewById<EditText>(R.id.et_email)
        val phoneNumberEditText = findViewById<EditText>(R.id.et_phone_number)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val confirmPasswordEditText = findViewById<EditText>(R.id.et_confirm_passowrd)
        val etBio = findViewById<EditText>(R.id.et_bio)
        val registerButton = findViewById<Button>(R.id.btn_register)
        val signIn = findViewById<TextView>(R.id.tx_sign_in)
        val selectImage = findViewById<TextView>(R.id.selectImage)
        imageView = findViewById<ImageView>(R.id.imageView)
        registerButton.setOnClickListener {
            if (usernameEditText.text.isEmpty()
                || emailEditText.text.isEmpty()
                || phoneNumberEditText.text.isEmpty()
                || passwordEditText.text.isEmpty()
                || confirmPasswordEditText.text.isEmpty()
                || uuid == ""
            ) {
                val alert = AlertDialog.Builder(this@RegisterActivity)
                alert.setTitle("Please enter credential")
                alert.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    View.OnClickListener {
                        dialog.dismiss()
                    }
                }
                alert.show()
            } else if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()){
                val alert = AlertDialog.Builder(this@RegisterActivity)
                alert.setTitle("Passwords do not match")
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
                                FirebaseDatabase.getInstance("https://loginsignup-auth-7e379-default-rtdb.firebaseio.com")
                            reference = database!!.getReference("Users")
                            val userProfile = User(etBio.text.toString(),usernameEditText.text.toString(),phoneNumberEditText.text.toString(),uuid)
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
                        }
                    }

            }
        }
        signIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        selectImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Pick your image to upload"
                ),
                22
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri);
                imageView!!.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadImage() {
        if (fileUri != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.setMessage("Uploading your image..")
            progressDialog.show()
            uuid = UUID.randomUUID().toString()
            val ref: StorageReference = FirebaseStorage.getInstance().getReference("ProfilePictures")
                .child(uuid!!)
            ref.putFile(fileUri!!).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Image Uploaded..", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Fail to Upload Image..", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
