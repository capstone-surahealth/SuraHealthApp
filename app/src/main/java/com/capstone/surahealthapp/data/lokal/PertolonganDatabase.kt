package com.capstone.surahealthapp.data.lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PertolonganPertamaEntity::class], version = 1)
abstract class PertolonganDatabase : RoomDatabase(){
    abstract val dao: PertolonganPertamaDao

    companion object{
        @Volatile
        private var instance:PertolonganDatabase? = null

        fun getInstance(context: Context):PertolonganDatabase?{
            if (instance == null){
                synchronized(PertolonganDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PertolonganDatabase::class.java,
                        "pp"
                    )
                        .addCallback(StartingPertolonganPertama(context))
                        .build()
                }
            }
            return instance
        }
    }
}