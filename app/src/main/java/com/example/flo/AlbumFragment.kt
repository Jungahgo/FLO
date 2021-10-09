package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    //binding을 쓰기 위해...

    val information = arrayListOf("수록곡","상세정보","영상")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false) //fragment 변수초기화

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment())
                    .commitAllowingStateLoss()
        }

        //binding 확인해줘~
        binding.albumAlbumLl.setOnClickListener{
            //토스트메세지.(.?): 사용자에게 간단하게 전달하는 팝업
            Toast.makeText(activity,"라일락",Toast.LENGTH_SHORT).show()
        }

        val albumAdapter = AlbumViewpagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        // 연결한 tablayout and 연결할 viewpager
        TabLayoutMediator(binding.albumContentTl, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()



        return binding.root
    }



}