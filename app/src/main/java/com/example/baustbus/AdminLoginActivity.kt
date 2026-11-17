package com.example.baustbus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // Admin Login Button Click Handler
        findViewById<Button>(R.id.btnAdminLogin).setOnClickListener {
            val username = findViewById<EditText>(R.id.etAdminUser).text.toString().trim()
            val password = findViewById<EditText>(R.id.etAdminPass).text.toString().trim()

            // Check the credentials
            if (username == "priyo" && password == "priyo96") {
                // If credentials are correct, navigate to AdminHomeActivity
                startActivity(Intent(this, AdminHomeActivity::class.java))
                finish() // Close the current activity
            } else {
                // Show an error message if credentials are invalid
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
