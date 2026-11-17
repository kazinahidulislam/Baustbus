package com.example.baustbus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // সঠিক ImageView ID ব্যবহার করুন
        val splashImage = findViewById<ImageView>(R.id.splashLogo)  // splashLogo ব্যবহার করতে হবে
        splashImage.setImageResource(R.drawable.splash_background)  // splash_background.xml

        // Delayed navigation to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start MainActivity after delay
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close SplashActivity
        }, 2000) // 2-second delay
    }
}
