package com.example.helldivers2companionapp.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helldivers2companionapp.databinding.ActivityAdminDashboardBinding
import com.example.helldivers2companionapp.ui.login.LoginActivity
import com.example.helldivers2companionapp.utils.SessionManager

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Security check
        if (!sessionManager.isLoggedIn() || sessionManager.getRole() != "ADMIN") {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // PANGGIL FUNGSI INI
        renderUI()
    }

    private fun renderUI() {
        // 1. Manage Stratagems
        binding.cardManageStratagems.setOnClickListener {
            startActivity(Intent(this, ManageStratagemsActivity::class.java))
        }

        // 2. War Status (Pastikan class WarStatusActivity sudah ada)
        binding.cardWarStatus.setOnClickListener {
            startActivity(Intent(this, WarStatusActivity::class.java))
        }

        // 3. Manage Missions (Pastikan class ManageMissionsActivity sudah ada)
        binding.cardManageMissions.setOnClickListener {
            startActivity(Intent(this, ManageMissionsActivity::class.java))
        }

        // 4. Analytics (Pastikan class AnalyticsActivity sudah ada)
        binding.cardAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }

        // 5. Logout
        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}