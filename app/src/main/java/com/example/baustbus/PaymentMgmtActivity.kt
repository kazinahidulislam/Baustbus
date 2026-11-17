package com.example.baustbus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baustbus.databinding.ActivityPaymentMgmtBinding
import com.google.firebase.firestore.FirebaseFirestore

class PaymentMgmtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentMgmtBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMgmtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load Existing Payment Numbers from Firestore
        loadExistingNumbers()
        // Set up Save Button listener
        setupSaveButton()
    }

    // Function to load existing payment numbers from Firestore
    private fun loadExistingNumbers() {
        db.collection("paymentNumbers") // Firestore-এর paymentNumbers কোলেকশন থেকে ডেটা ফেচ করা হচ্ছে
            .document("payment")  // Payment document থেকে ডেটা রিড করা হচ্ছে
            .get()
            .addOnSuccessListener { document ->
                // Fill the EditText fields with existing data
                binding.etBkash.setText(document.getString("bkash"))
                binding.etNagad.setText(document.getString("nagad"))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load payment numbers", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to set up the save button functionality
    private fun setupSaveButton() {
        binding.btnSaveNumber.setOnClickListener {
            val bkash = binding.etBkash.text.toString()
            val nagad = binding.etNagad.text.toString()

            // Validate numbers before saving
            if (validateNumbers(bkash, nagad)) {
                saveNumbers(bkash, nagad)
            }
        }
    }

    // Function to validate Bkash and Nagad numbers
    private fun validateNumbers(bkash: String, nagad: String): Boolean {
        return when {
            bkash.length != 11 -> {
                binding.etBkash.error = "সঠিক নম্বর দিন" // Bkash নম্বরের জন্য সঠিক ইনপুট নিশ্চিত করা হচ্ছে
                false
            }
            nagad.length != 11 -> {
                binding.etNagad.error = "সঠিক নম্বর দিন" // Nagad নম্বরের জন্য সঠিক ইনপুট নিশ্চিত করা হচ্ছে
                false
            }
            else -> true
        }
    }

    // Function to save payment numbers to Firestore
    private fun saveNumbers(bkash: String, nagad: String) {
        val data = hashMapOf(
            "bkash" to bkash,
            "nagad" to nagad
        )

        db.collection("paymentNumbers") // Firestore এর paymentNumbers কোলেকশন
            .document("payment") // Payment document-এ ডেটা সেট করা হচ্ছে
            .set(data)  // ডেটা Firestore-এ আপডেট হচ্ছে
            .addOnSuccessListener {
                Toast.makeText(this, "সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show() // সফলভাবে ডেটা সংরক্ষণ হলে মেসেজ
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "ত্রুটি: ${e.message}", Toast.LENGTH_SHORT).show() // কোনো সমস্যা হলে ত্রুটি মেসেজ
            }
    }
}
