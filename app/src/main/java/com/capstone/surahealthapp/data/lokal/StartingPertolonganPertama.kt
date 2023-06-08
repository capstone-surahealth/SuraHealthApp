package com.capstone.surahealthapp.data.lokal

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.capstone.surahealthapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import timber.log.Timber
import java.io.BufferedReader

class StartingPertolonganPertama(private val context: Context) : RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            fillWithStartingPP(context)
        }
    }

    //Filling database with the data from JSON
    private suspend fun fillWithStartingPP(context: Context){
        //obtaining instance of data access object
        val dao = PertolonganDatabase.getInstance(context)?.dao

        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val pPertama = loadJSONArray(context)
            if (pPertama != null){
                //looping through the variable as specified fields are loaded with data
                for (i in 0 until pPertama.length()){
                    //variable to obtain the JSON object
                    val item = pPertama.getJSONObject(i)
                    //Using the JSON object to assign data
                    val ppTitle = item.getString("pp-title")
                    val ppDescription = item.getString("pp-description")
                    val ppPhotoUrl = item.getString("pp-photoUrl")

                    //data loaded to the entity
                    val ppEntity = PertolonganPertamaEntity(
                        ppTitle, ppPhotoUrl, ppDescription
                    )

                    //using dao to insert data to the database
                    dao?.insertPertolongan(ppEntity)
                }
            }
        }
        //error when exception occurs
        catch (e: JSONException) {
            Timber.d("fillWithStartingPP: $e")
        }
    }

    // loads JSON data
    private fun loadJSONArray(context: Context): JSONArray?{
        //obtain input byte
        val inputStream = context.resources.openRawResource(R.raw.pp)
        //using Buffered reader to read the inputstream byte
        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}