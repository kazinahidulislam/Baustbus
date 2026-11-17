package com.example.baustbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        // Bus Management Button - Navigates to BusMgmtActivity
        findViewById<Button>(R.id.btnBusMgmt).setOnClickListener {
            startActivity(Intent(this, BusMgmtActivity::class.java))
        }

        // Payment Management Button - Navigates to PaymentMgmtActivity
        findViewById<Button>(R.id.btnPaymentMgmt).setOnClickListener {
            startActivity(Intent(this, PaymentMgmtActivity::class.java))
        }

        // Booking List Button - Navigates to BookingListActivity
        findViewById<Button>(R.id.btnBookingList).setOnClickListener {
            startActivity(Intent(this, BookingListActivity::class.java))
        }
    }
}
