package com.capstone.surahealthapp.view.pertolonganpertama

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surahealthapp.data.lokal.PertolonganDatabase
import com.capstone.surahealthapp.databinding.ActivityPertolonganPertamaBinding

class PertolonganPertamaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertolonganPertamaBinding
    private val pertolonganPertamaAdapter by lazy {PertolonganPertamaAdapter()}
    private lateinit var viewModel: PertolonganPertamaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertolonganPertamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPertolonganPertama.layoutManager = LinearLayoutManager(this)
        setupViewModel()
        showAllPertolonganPertama()
    }

    private fun setupViewModel() {
        val ppDatabase = PertolonganDatabase.getInstance(this)
        val myViewModelFactory = PPViewModelFactory(ppDatabase!!)

        viewModel = ViewModelProvider(this, myViewModelFactory).get(PertolonganPertamaViewModel::class.java)
    }

    private fun showAllPertolonganPertama(){
        //submitting data to the adapter which the maps it to recyclerview
        viewModel.pps.observe(this, Observer { result ->
            pertolonganPertamaAdapter.submitList(result)
            binding.rvPertolonganPertama.adapter = pertolonganPertamaAdapter
        })
    }
}