package com.sgroid.myhoroscope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.dashboard
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    startActivity( Intent (getApplicationContext(), Home::class.java));
                    overridePendingTransition(0, 0);
                    return@setOnItemSelectedListener true
                }
                R.id.dashboard->{
                    startActivity( Intent(getApplicationContext(),Dashboard::class.java));
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true
                }
                R.id.about->{
                    startActivity( Intent(getApplicationContext(),About::class.java));
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}