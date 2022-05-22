package com.example.myuniversity.ui

import androidx.lifecycle.ViewModel
import com.example.myuniversity.UnivRepository
import com.example.myuniversity.local.room.entity.UnivEntity

class UnivViewModel(private val univRepository: UnivRepository): ViewModel() {

    fun getHeadlineUniv() = univRepository.getHeadlineUniv()
    fun getBookmarkedUniv() = univRepository.getBookmarkedUniv()

    fun saveUniv(univ: UnivEntity) {
        univRepository.setBookmarkedUniv(univ, true)
    }

    fun deleteUniv(univ: UnivEntity) {
        univRepository.setBookmarkedUniv(univ, false)
    }

    
}