package com.example.baustbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.baustbus.UserLoginActivity
import com.example.baustbus.AdminLoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // User Button click listener
        findViewById<Button>(R.id.btnUser).setOnClickListener {
            // Start User Login Activity
            startActivity(Intent(this, UserLoginActivity::class.java))
        }

        // Admin Button click listener
        findViewById<Button>(R.id.btnAdmin).setOnClickListener {
            // Start Admin Login Activity
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
