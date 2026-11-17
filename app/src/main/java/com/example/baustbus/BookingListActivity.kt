package com.example.baustbus

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baustbus.databinding.ActivityBookingListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingListBinding
    private val bookingList = mutableListOf<Booking>()
    private lateinit var adapter: BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadBookings()  // Ensure to call this method in onCreate
    }

    private fun setupRecyclerView() {
        adapter = BookingAdapter(bookingList)
        binding.rvBookings.apply {
            layoutManager = LinearLayoutManager(this@BookingListActivity)
            adapter = this@BookingListActivity.adapter
        }
    }

    private fun loadBookings() {
        // Show the progress bar when loading starts
        binding.progressBar.visibility = View.VISIBLE

        Firebase.database.getReference("bookings")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Clear the existing booking list
                    bookingList.clear()
                    snapshot.children.forEach { dataSnapshot ->
                        // Get the booking data and add to the list
                        dataSnapshot.getValue(Booking::class.java)?.let {
                            bookingList.add(it.copy(id = dataSnapshot.key ?: ""))
                        }
                    }
                    // Notify adapter that data has changed
                    adapter.notifyDataSetChanged()
                    // Hide the progress bar when data is loaded
                    binding.progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    // Hide the progress bar in case of failure
                    binding.progressBar.visibility = View.GONE
                    // Show error message
                    Toast.makeText(
                        this@BookingListActivity,
                        "ডেটা লোড করতে সমস্যা: ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    data class Booking(
        var id: String = "",
        val busId: String = "",
        val userId: String = "",
        var status: String = "pending",
        val timestamp: Long = System.currentTimeMillis()
    )
}
