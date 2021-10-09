package com.example.flo

import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//adapter는 이미지를 가져올거라서 인자를 받아야함
class BannerViewpagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment){

    //fragementlist가 private이 아니라면 데이터가 외부로 나갈 수 있음 (보안성 위배)
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

//    override fun getItemCount(): Int {
//        //fragmentlist에 있는 item 갯수
//        return fragmentlist.size
//    }

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size -1)

    }
}