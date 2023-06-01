package com.capstone.surahealthapp.view.detailpp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.databinding.ActivityDetailPertolonganPertamaBinding
import com.capstone.surahealthapp.utils.ResultState
import com.capstone.surahealthapp.view.common.ViewModelFactory
import com.capstone.surahealthapp.view.pertolonganpertama.PertolonganPertamaViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailPertolonganPertamaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPertolonganPertamaBinding
    private lateinit var viewModel: DetailPertolonganPertamaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPertolonganPertamaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        val itemId = intent.getLongExtra("item_id", 0)
        getItemDetail(itemId)
    }

    private fun setupViewModel(){
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailPertolonganPertamaViewModel::class.java]
    }

    private fun getItemDetail(itemId: Long){
        lifecycleScope.launch {
            viewModel.resultStateFlow.collect{resultState ->
                when(resultState){
                    is ResultState.Loading -> { }
                    is ResultState.Success -> {
                        val itemDetail = resultState.data
                        binding.apply {
                            tvNamePp.text = itemDetail.pertolongan_pertama
                            tvDeskripsiPp.text = itemDetail.deskripsi
                            Glide.with(binding.root)
                                .load(itemDetail.photoUrl)
                                .fitCenter()
                                .into(ivPp)
                        }
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(this@DetailPertolonganPertamaActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModel.getPertolonganPertamaById(itemId)
    }
}