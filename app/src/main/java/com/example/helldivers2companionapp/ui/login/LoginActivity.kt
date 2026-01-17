package com.example.helldivers2companionapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.data.model.User
import com.example.helldivers2companionapp.databinding.ActivityLoginBinding
import com.example.helldivers2companionapp.ui.admin.AdminDashboardActivity
import com.example.helldivers2companionapp.ui.player.PlayerDashboardActivity
import com.example.helldivers2companionapp.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        sessionManager = SessionManager(this)

        // 1. Cek apakah user sudah login sebelumnya
        checkExistingSession()

        // 2. Jalankan Auto-Seeding (Isi data otomatis di balik layar)
        performAutoSeed()

        // 3. Setup Tombol Login
        setupListeners()
    }

    private fun performAutoSeed() {
        lifecycleScope.launch {
            // 1. Cek Admin
            val checkAdmin = database.userDao().login("admin", "123")

            if (checkAdmin == null) {
                // Buat 1 Admin
                database.userDao().insertUser(User(username = "admin", password = "123", role = "ADMIN"))

                // 2. Buat Helldiver 1 sampai 12 (Looping agar cepat)
                for (i in 1..12) {
                    val kills = (100..2000).random()
                    val deaths = (10..100).random()
                    val missions = (5..50).random()

                    database.userDao().insertUser(
                        User(
                            username = "helldiver$i",
                            password = "123",
                            role = "USER", // Pastikan role ini sama dengan 'USER' di DAO
                            kills = kills,
                            deaths = deaths,
                            missionsCompleted = missions
                        )
                    )
                }
                android.util.Log.d("AUTO_SEED", "Admin & 12 Helldivers Created!")
            }
        }
    }

    private fun checkExistingSession() {
        if (sessionManager.isLoggedIn()) {
            val role = sessionManager.getRole()
            if (role == "ADMIN") {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
            } else {
                startActivity(Intent(this, PlayerDashboardActivity::class.java))
            }
            finish()
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(username, password)
        }

        // Tombol Seed sudah dihapus, jadi tidak ada listener tvSeedData disini
    }

    private fun loginUser(username: String, pass: String) {
        lifecycleScope.launch {
            val user = database.userDao().login(username, pass)
            if (user != null) {
                sessionManager.saveSession(user.username, user.role, user.id)
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                if (user.role == "ADMIN") {
                    startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                } else {
                    startActivity(Intent(this@LoginActivity, PlayerDashboardActivity::class.java))
                }
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}