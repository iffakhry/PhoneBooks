package com.example.phonesmkkoding.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.phonesmkkoding.model.PhoneModel

@Dao
interface PhoneDao {
    @Query("SELECT * from phone")
    fun getAllPhone(): LiveData<List<PhoneModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phone: PhoneModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(phone: List<PhoneModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(phone: PhoneModel)

    @Delete()
    suspend fun delete(phone: PhoneModel)

    @Query("DELETE FROM phone")
    suspend fun deleteAll()
}