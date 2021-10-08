package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    //binding을 쓰기 위해...


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false) //fragment 변수초기화

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment())
                    .commitAllowingStateLoss()
        }
        binding.album1.setOnClickListener{
            //토스트메세지.(.?): 사용자에게 간단하게 전달하는 팝업
            Toast.makeText(activity,"라일락",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }



}