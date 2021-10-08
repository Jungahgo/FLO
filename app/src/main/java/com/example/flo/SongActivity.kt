package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

//AppCompatActivity - Andriod에서 Activity 기능을 사용할 수 있게 함

class SongActivity : AppCompatActivity() {

    //변수선언 방법: var - 선언 이후 값 변경 가능 / val - 선언 이후 값 변경 불가능
    //vat test1 : Int = 2

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //activity 최상뷰를 마음대로 쓸거야

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songNameTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")

        }

        binding.songNuguBtnDownIv.setOnClickListener{//songNuguBtnDown을 눌렀을 때 나올 작업
            //->activity를 끄는 작업을 적을 것임
            finish()
        }

        //재생, 정지
        binding.songMiniplayerPlayIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songMiniplayerMvpauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        //repeat 부분
        binding.songRepeatOffIv.setOnClickListener {
            binding.songRepeatOnIv.visibility = View.VISIBLE
            binding.songRepeatOffIv.visibility = View.GONE
        }

        binding.songRepeatOnIv.setOnClickListener {
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatOffIv.visibility = View.VISIBLE
        }

        //random 부분
        binding.songRandomOffIv.setOnClickListener {
            binding.songRandomOffIv.visibility = View.GONE
            binding.songRandomOnIv.visibility = View.VISIBLE
        }

        binding.songRandomOnIv.setOnClickListener {
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomOffIv.visibility = View.VISIBLE
        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerPlayIv.visibility = View.VISIBLE
            binding.songMiniplayerMvpauseIv.visibility = View.GONE
        }else{
            binding.songMiniplayerPlayIv.visibility = View.GONE
            binding.songMiniplayerMvpauseIv.visibility = View.VISIBLE
        }
    }
}
