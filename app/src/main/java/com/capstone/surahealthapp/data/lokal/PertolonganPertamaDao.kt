package com.capstone.surahealthapp.data.lokal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PertolonganPertamaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPertolongan(pertolonganEntity: PertolonganPertamaEntity)

    @Query("SELECT * FROM Pertolongan_table ORDER BY id ASC")
    fun getAllPertolonganPertama(): LiveData<List<PertolonganPertamaEntity>>

    @Query("SELECT * FROM Pertolongan_table WHERE id = :itemId")
    fun getPertolonganPertamaById(itemId: Long): LiveData<PertolonganPertamaEntity>
}