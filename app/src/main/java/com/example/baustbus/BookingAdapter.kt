package com.example.baustbus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baustbus.databinding.ItemBookingBinding
import java.text.SimpleDateFormat
import java.util.*

class BookingAdapter(private val bookings: List<BookingListActivity.Booking>) :
    RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]
        with(holder.binding) {
            // Use context from binding.root
            tvBusId.text = root.context.getString(R.string.bus_id, booking.busId)
            tvUserId.text = root.context.getString(R.string.user_id, booking.userId)
            tvStatus.text = root.context.getString(R.string.status, booking.status.replaceFirstChar {
                it.uppercase()
            })

            // Format the timestamp using SimpleDateFormat
            val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale("bn"))
            tvTimestamp.text = root.context.getString(R.string.timestamp, dateFormat.format(Date(booking.timestamp)))
        }
    }

    override fun getItemCount() = bookings.size
}
