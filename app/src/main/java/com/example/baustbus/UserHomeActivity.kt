package com.example.baustbus

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baustbus.databinding.ActivityUserHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class UserHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val busList = mutableListOf<Bus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatePicker()
        setupSearchButton()
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.etDate.setText("%02d-%02d-%04d".format(day, month + 1, year))
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSearchButton() {
        binding.btnSearch.setOnClickListener {
            val from = binding.etFrom.text.toString()
            val to = binding.etTo.text.toString()
            if (from.isEmpty() || to.isEmpty()) {
                Toast.makeText(this, "লোকেশন সিলেক্ট করুন", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loadBuses("$from-to-$to")
        }
    }

    private fun loadBuses(route: String) {
        db.collection("buses") // Firestore এর buses কোলেকশন
            .whereEqualTo("route", route)  // Route ফিল্টার করে ডেটা রিড করা
            .get()
            .addOnSuccessListener { querySnapshot ->
                busList.clear()
                for (document in querySnapshot) {
                    // Firestore ডকুমেন্ট থেকে Bus অবজেক্টে রূপান্তর করা
                    val bus = document.toObject(Bus::class.java).copy(id = document.id)
                    busList.add(bus)
                }
                updateListView()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "ত্রুটি: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateListView() {
        val items = busList.map {
            "${it.busName}\nসময়: ${it.departure} | ভাড়া: ${it.fare}৳"
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.lvBuses.adapter = adapter

        binding.lvBuses.setOnItemClickListener { _, _, position, _ ->
            // BookTicketActivity-তে বাস আইডি পাস করুন
            val intent = Intent(this, BookTicketActivity::class.java)
            intent.putExtra("BUS_ID", busList[position].id)
            startActivity(intent)
        }
    }

    // বাস ডেটার মডেল ক্লাস
    data class Bus(
        val id: String = "",
        val busName: String = "",
        val route: String = "",
        val departure: String = "",
        val arrival: String = "",
        val fare: String = ""
    )
}
