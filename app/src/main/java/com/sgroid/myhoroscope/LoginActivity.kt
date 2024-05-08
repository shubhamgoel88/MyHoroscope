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
import com.google.firebase.auth.FirebaseAuth
import com.sgroid.myhoroscope.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                progressDialog.show()
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this.applicationContext,
                            "Login Successful",
                            Toast.LENGTH_LONG
                        ).show()
                        progressDialog.cancel()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this.applicationContext,
                            "Login failure" + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                        progressDialog.cancel()
                    }
            } else {
                Toast.makeText(
                    this.applicationContext,
                    "Email or Password cannot be blank.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.loginSignup.setOnClickListener {
            startActivity(Intent(this.applicationContext, MainActivity::class.java))
        }

        val registerText = SpannableStringBuilder()
            .append("Not Registered? ")
            .bold { scale(1.5f, { append("SignUp") }) }
        binding.loginSignup.text = registerText

        val resetText = SpannableStringBuilder()
            .append("Forgot Password? ")
            .bold { scale(1.5f, { append("Reset") }) }
        binding.passwordReset.text = resetText
        binding.passwordReset.setOnClickListener {
            val email = binding.loginEmail.text.toString()

            if (!TextUtils.isEmpty(email)) {
                progressDialog.setTitle("Sending Mail")
                progressDialog.show()
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        progressDialog.cancel()
                        Toast.makeText(this.applicationContext, "Email Sent", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener {
                        progressDialog.cancel()
                        Toast.makeText(
                            this.applicationContext,
                            "Failure :" + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {
                Toast.makeText(
                    this.applicationContext,
                    "Please enter valid email.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}