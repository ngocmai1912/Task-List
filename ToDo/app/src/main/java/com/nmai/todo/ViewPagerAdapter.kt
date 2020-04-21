package com.nmai.todo

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragment : FragmentManager) : FragmentStatePagerAdapter(fragment){
    var listFragment = mutableListOf<Fragment>()
    val listTitle = listOf("Home", "Important", "Done")
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }
    fun addFragment(list : List<Fragment>){
        listFragment.addAll(list)
    }
}