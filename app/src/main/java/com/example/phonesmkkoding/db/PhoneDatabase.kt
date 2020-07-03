package com.example.phonesmkkoding.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.phonesmkkoding.dao.PhoneDao
import com.example.phonesmkkoding.model.PhoneModel


@Database(entities = arrayOf(PhoneModel::class), version = 1, exportSchema = false)
public abstract class PhoneDatabase: RoomDatabase()  {

    abstract fun phoneDao(): PhoneDao

    companion object{
        @Volatile
        private var INSTANCE: PhoneDatabase? = null

        fun getDatabase(context: Context): PhoneDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null ){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneDatabase::class.java,
                    "phone_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}