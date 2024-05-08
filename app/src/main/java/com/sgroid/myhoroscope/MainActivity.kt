package com.sgroid.myhoroscope

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.scale
import com.google.common.io.Files.append
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sgroid.myhoroscope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFireStore: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()
        binding.signUp.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val mobile = binding.mobile.text.toString()
            val password = binding.password.text.toString()
            val confrmpassword = binding.confrmPassword.text.toString()
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(
                    password
                )
            ) {
                Toast.makeText(
                    this.applicationContext,
                    "All fields are mandatory",
                    Toast.LENGTH_LONG
                ).show()
            } else if (confrmpassword != password) {
                Toast.makeText(
                    this.applicationContext,
                    "password mismatch",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                progressDialog.show()
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        progressDialog.cancel()
                        Toast.makeText(
                            this.applicationContext,
                            "SIGNuP Successful",
                            Toast.LENGTH_LONG
                        ).show()
                        FirebaseAuth.getInstance().uid?.let { it1 ->
                            firebaseFireStore.collection("User")
                                .document(it1)
                                .set(UserModel(name = name, number = mobile, email = email))
                        }
                        startActivity(Intent(this.applicationContext, LoginActivity::class.java))
                    }
                    .addOnFailureListener {
                        progressDialog.cancel()
                        Toast.makeText(
                            this.applicationContext,
                            "sIGNuP failure" + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }

        }
        val loginText = SpannableStringBuilder()
            .append("Already registered? ")
            .bold { scale(1.5f, { append("SignIn") }) }
        binding.loginfromSignUp.text = loginText
        binding.loginfromSignUp.setOnClickListener {
            startActivity(Intent(this.applicationContext, LoginActivity::class.java))
        }
    }
}