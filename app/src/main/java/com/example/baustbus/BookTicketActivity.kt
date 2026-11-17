package com.example.baustbus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baustbus.databinding.ActivityBookTicketBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BookTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookTicketBinding
    private lateinit var busId: String
    private val paymentRef = FirebaseDatabase.getInstance().getReference("paymentNumbers") // Correct reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busId = intent.getStringExtra("BUS_ID") ?: return finish()

        loadBusDetails()
        loadPaymentNumbers()

        // Book Button Click Listener
        binding.btnBook.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
                Toast.makeText(this, "Login required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Creating a booking ID and saving booking details to Firebase
            val bookingId = "${busId}_${userId}_${System.currentTimeMillis()}"
            val bookingRef = FirebaseDatabase.getInstance().getReference("bookings/$bookingId")

            // Writing to Firebase Database
            bookingRef.child("userId").setValue(userId)
            bookingRef.child("busId").setValue(busId)
            bookingRef.child("status").setValue("pending")
            bookingRef.child("timestamp").setValue(System.currentTimeMillis())

            Toast.makeText(this, "Booking requested!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Call Button Click Listener
        binding.btnCall.setOnClickListener {
            val number = binding.tvPaymentNumber.text.toString()
            if (number.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
            }
        }
    }

    // Fetch bus details from Firebase and set to TextView
    private fun loadBusDetails() {
        FirebaseDatabase.getInstance().getReference("buses/$busId")
            .get().addOnSuccessListener { dataSnapshot ->
                val bus = dataSnapshot.getValue(Bus::class.java)
                bus?.let {
                    // Use string resources with placeholders
                    binding.tvBusInfo.text = getString(R.string.bus_info_format, bus.busName, bus.route, bus.departure, bus.fare)
                }
            }
    }

    // Fetch payment numbers (e.g., Bkash) from Firebase
    private fun loadPaymentNumbers() {
        paymentRef.get().addOnSuccessListener { dataSnapshot ->
            val bkash = dataSnapshot.child("bkash").getValue(String::class.java) ?: ""
            // Use string resources with placeholders
            binding.tvPaymentNumber.text = getString(R.string.bkash_payment_info, bkash)
        }
    }
}
