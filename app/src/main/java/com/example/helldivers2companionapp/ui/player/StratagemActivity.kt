package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityStratagemListBinding
import kotlinx.coroutines.launch

class StratagemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStratagemListBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: StratagemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStratagemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = StratagemAdapter(emptyList())
        binding.rvStratagems.layoutManager = LinearLayoutManager(this)
        binding.rvStratagems.adapter = adapter
    }

    private fun loadData() {
        lifecycleScope.launch {
            // Gunakan .collect untuk mengambil data dari Flow
            database.stratagemDao().getAllStratagems().collect { list ->
                if (list.isEmpty()) {
                    // Tampilkan pesan kosong jika list dari Flow kosong
                    binding.tvEmptyState.visibility = android.view.View.VISIBLE
                    binding.rvStratagems.visibility = android.view.View.GONE
                } else {
                    // Sembunyikan pesan kosong dan update adapter
                    binding.tvEmptyState.visibility = android.view.View.GONE
                    binding.rvStratagems.visibility = android.view.View.VISIBLE

                    adapter.updateData(list)
                }
            }
        }
    }
}