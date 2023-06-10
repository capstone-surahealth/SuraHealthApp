package com.capstone.surahealthapp.view.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.surahealthapp.MapsViewModel
import com.capstone.surahealthapp.data.repository.PertolonganPertamaRepository
import com.capstone.surahealthapp.data.repository.RumahSakitRepository
import com.capstone.surahealthapp.di.Injection
import com.capstone.surahealthapp.view.detailpp.DetailPertolonganPertamaViewModel
import com.capstone.surahealthapp.view.pertolonganpertama.PertolonganPertamaViewModel
import com.capstone.surahealthapp.view.rumahsakit.RumahSakitViewModel

class ViewModelFactory(
    private val pertolonganPertamaRepository: PertolonganPertamaRepository,
    private val rumahSakitRepository: RumahSakitRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailPertolonganPertamaViewModel::class.java) -> {
                DetailPertolonganPertamaViewModel(pertolonganPertamaRepository) as T
            }
            modelClass.isAssignableFrom(PertolonganPertamaViewModel::class.java) -> {
                PertolonganPertamaViewModel(pertolonganPertamaRepository) as T
            }
            modelClass.isAssignableFrom(RumahSakitViewModel::class.java) -> {
                RumahSakitViewModel(rumahSakitRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(rumahSakitRepository) as T
            }

            else -> throw  IllegalArgumentException("Unknown ViewModel class: "+ modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideRumahSakitRepository(context)
                )

            }.also { instance = it }
        }
    }
}