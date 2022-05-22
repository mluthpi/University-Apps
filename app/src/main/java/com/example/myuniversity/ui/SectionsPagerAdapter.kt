package com.example.myuniversity.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = UniversityFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(UniversityFragment.ARG_TAB, UniversityFragment.TAB_UNIV)
        } else {
            bundle.putString(UniversityFragment.ARG_TAB, UniversityFragment.TAB_BOOKMARK)
        }
        fragment.arguments = bundle
        return fragment
    }

}