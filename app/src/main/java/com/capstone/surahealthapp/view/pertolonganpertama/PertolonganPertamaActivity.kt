package com.capstone.surahealthapp.view.pertolonganpertama

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.databinding.ActivityPertolonganPertamaBinding
import com.capstone.surahealthapp.view.common.ViewModelFactory

class PertolonganPertamaActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
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
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[PertolonganPertamaViewModel::class.java]
    }

    private fun showAllPertolonganPertama(){
        viewModel.getAllPertolonganPertama().observe(this, Observer { result ->
            pertolonganPertamaAdapter.submitList(result)
            binding.rvPertolonganPertama.adapter = pertolonganPertamaAdapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)

        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchPertolonganPertama(searchQuery).observe(this) { list ->
            list.let {
                pertolonganPertamaAdapter.submitList(it)
            }
        }

    }
}