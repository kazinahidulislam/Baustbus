package com.example.baustbus

// Bus data class to represent the bus details
data class Bus(
    val id: String = "",          // Firestore auto-generated ID
    val busName: String = "",     // Name of the bus
    val route: String = "",       // Route (e.g., "Dhaka-Chittagong")
    val departure: String = "",  // Departure time (e.g., "08:00 AM")
    val arrival: String = "",    // Arrival time (e.g., "12:00 PM")
    val fare: String = ""         // Fare for the bus ride (e.g., "1200")
)
