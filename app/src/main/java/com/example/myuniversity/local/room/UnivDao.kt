package com.example.myuniversity.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myuniversity.local.room.entity.UnivEntity


@Dao
interface UnivDao {
    @Query("SELECT * FROM university ORDER BY title DESC")
    fun getUniv(): LiveData<List<UnivEntity>>

    @Query("SELECT * FROM university where bookmarked = 1")
    fun getBookmarkedUniv(): LiveData<List<UnivEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUniv(university: List<UnivEntity>)

    @Update
    fun updateUniv(university: UnivEntity)

    @Query("DELETE FROM university where bookmarked= 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM university where title = :title AND bookmarked = 1)")
    fun isUnivBookmarked(title: String): Boolean
}