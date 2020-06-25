package com.android.wan.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PublicPagerAdapter internal constructor(
    fm: FragmentManager?,
    private val fragments: List<Fragment>,
    private val mTitles: List<String>
) : FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

}