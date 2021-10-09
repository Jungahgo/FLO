package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
//AlbumViewpagerAdapter 수록곡, 상세정보, 영상

class AlbumViewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3 //수록곡, 상세정보, 영상

    override fun createFragment(position: Int): Fragment {
        //수록곡, 상세정보, 영상을 눌렀을 때 나옴
        //when은 switch문과 비슷

        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}