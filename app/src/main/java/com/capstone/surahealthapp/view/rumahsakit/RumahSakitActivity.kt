package com.capstone.surahealthapp.view.rumahsakit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surahealthapp.databinding.ActivityRumahSakitBinding
import com.capstone.surahealthapp.utils.ResultState
import com.capstone.surahealthapp.view.common.ViewModelFactory
import kotlinx.coroutines.launch

class RumahSakitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRumahSakitBinding
    private lateinit var viewModel: RumahSakitViewModel
    private lateinit var adapter: RumahSakitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRumahSakitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RumahSakitAdapter()
        binding.rvRumahSakit.layoutManager = LinearLayoutManager(this)
        binding.rvRumahSakit.adapter = adapter
        setupViewModel()
        showAllRumahSakit()

    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[RumahSakitViewModel::class.java]
    }

    private fun showAllRumahSakit(){
        lifecycleScope.launch{
            viewModel.rmState.collect{ resultState ->
                when(resultState) {
                    is ResultState.Loading -> {

                    }
                    is ResultState.Success -> {
                        val rumahSakitList = resultState.data
                        adapter.submitList(rumahSakitList)
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(this@RumahSakitActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.getAllRumahSakit()
    }


}