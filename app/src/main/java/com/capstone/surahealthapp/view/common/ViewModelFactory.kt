package com.capstone.surahealthapp.view.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.surahealthapp.data.repository.SuraRepository
import com.capstone.surahealthapp.di.Injection
import com.capstone.surahealthapp.view.detailpp.DetailPertolonganPertamaViewModel
import com.capstone.surahealthapp.view.pertolonganpertama.PertolonganPertamaViewModel

class ViewModelFactory(
    private val suraRepository: SuraRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailPertolonganPertamaViewModel::class.java) -> {
                DetailPertolonganPertamaViewModel(suraRepository) as T
            }
            modelClass.isAssignableFrom(PertolonganPertamaViewModel::class.java) -> {
                PertolonganPertamaViewModel(suraRepository) as T
            }

            else -> throw  IllegalArgumentException("Unknown ViewModel class: "+ modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                val repository: SuraRepository = Injection.provideRepository(context)
                ViewModelFactory(repository)
            }.also { instance = it }
        }
    }
}