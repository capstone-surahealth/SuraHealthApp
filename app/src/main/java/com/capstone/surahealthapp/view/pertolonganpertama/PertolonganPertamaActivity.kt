package com.capstone.surahealthapp.view.pertolonganpertama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.databinding.ActivityPertolonganPertamaBinding
import com.capstone.surahealthapp.utils.ResultState
import com.capstone.surahealthapp.view.common.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PertolonganPertamaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertolonganPertamaBinding
    private lateinit var pertolonganPertamaAdapter: PertolonganPertamaAdapter
    private lateinit var viewModel: PertolonganPertamaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertolonganPertamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pertolonganPertamaAdapter = PertolonganPertamaAdapter()
        binding.rvPertolonganPertama.layoutManager = LinearLayoutManager(this)
        binding.rvPertolonganPertama.adapter = pertolonganPertamaAdapter
        setupViewModel()
        showAllPertolonganPertama()


    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[PertolonganPertamaViewModel::class.java]
    }

    private fun showAllPertolonganPertama(){
        lifecycleScope.launch{
            viewModel.resultStateFlow.collect{ resultState ->
                when(resultState) {
                    is ResultState.Loading -> {

                    }
                    is ResultState.Success -> {
                        val pertolonganPertamaList = resultState.data
                        pertolonganPertamaAdapter.submitList(pertolonganPertamaList)
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(this@PertolonganPertamaActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModel.getAllPertolonganPertama()
    }
}