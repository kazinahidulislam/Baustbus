package com.example.baustbus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserSignupActivity : Activity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etLocation: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etLocation = findViewById(R.id.etLocation)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        // Set Sign Up button click listener
        btnSignUp.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validate input
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || location.isEmpty() || password.length < 6) {
                Toast.makeText(this, "Fill-up all fields correctly.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user with Firebase Auth
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Get the unique ID of the newly registered user
                        val uid = mAuth.currentUser?.uid ?: return@addOnCompleteListener

                        // Save user data to Firebase Realtime Database
                        val userModel = UserModel(username, email, phone, location)
                        dbRef.child(uid).setValue(userModel)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, UserLoginActivity::class.java))
                                finish() // Close the signup activity
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Show error if user registration fails
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    data class UserModel(
        val username: String,
        val email: String,
        val phone: String,
        val location: String
    )
}
