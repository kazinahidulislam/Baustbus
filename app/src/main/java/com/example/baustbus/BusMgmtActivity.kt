package com.example.baustbus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baustbus.databinding.ActivityBusMgmtBinding
import com.google.firebase.firestore.FirebaseFirestore

class BusMgmtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusMgmtBinding
    private val db = FirebaseFirestore.getInstance()  // Firestore ইনিশিয়ালাইজ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusMgmtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add new bus when button clicked
        binding.btnAddBus.setOnClickListener {
            val bus = Bus(
                busName = binding.etBusName.text.toString(),
                route = binding.etRoute.text.toString(),
                departure = binding.etDeparture.text.toString(),
                arrival = binding.etArrival.text.toString(),
                fare = binding.etFare.text.toString()
            )

            // Validate the bus details
            if (validateBus(bus)) {
                // Add bus details to Firestore database
                addBusToFirestore(bus)
            }
        }
    }

    // Firestore এ বাস অ্যাড করার কোড
    private fun addBusToFirestore(bus: Bus) {
        db.collection("buses")
            .add(bus)  // Firestore-এ বাস ডেটা অ্যাড করা হচ্ছে
            .addOnSuccessListener {
                Toast.makeText(this, "বাস যোগ হয়েছে!", Toast.LENGTH_SHORT).show()
                clearFields()  // সঠিকভাবে অ্যাড হলে ইনপুট ফিল্ডস ক্লিয়ার হবে
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "ত্রুটি: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // বাস ডেটার ইনপুট ভ্যালিডেশন
    private fun validateBus(bus: Bus): Boolean {
        return when {
            bus.busName.isEmpty() -> showError("বাসের নাম দিন")
            bus.route.isEmpty() -> showError("রুট দিন")
            bus.departure.isEmpty() -> showError("ডিপার্চার সময় দিন")
            bus.fare.isEmpty() -> showError("ভাড়া দিন")
            else -> true
        }
    }

    // টোস্ট মেসেজের মাধ্যমে এরর দেখানোর কোড
    private fun showError(msg: String): Boolean {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        return false
    }

    // ইনপুট ফিল্ডস ক্লিয়ার করার কোড
    private fun clearFields() {
        listOf(
            binding.etBusName,
            binding.etRoute,
            binding.etDeparture,
            binding.etArrival,
            binding.etFare
        ).forEach { it.text?.clear() }
    }

    // বাসের ডেটা হোল্ড করার জন্য ডেটা ক্লাস
    data class Bus(
        val busName: String = "",
        val route: String = "",
        val departure: String = "",
        val arrival: String = "",
        val fare: String = ""
    )
}
