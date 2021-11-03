package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

// AppCompatActivity - Andriod에서 Activity 기능을 사용할 수 있게 함
class MainActivity : AppCompatActivity() {

    //binding 객체 선언
    lateinit var binding: ActivityMainBinding

    //Gson
    private var gson: Gson = Gson()
    private var song: Song = Song()

    //onCreate: main함수 느낌. 반드시 필요함, 레이아웃 생성. 초기화 컴포넌트 불러옴
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        //레이아웃(root뷰) 표시
        setContentView(binding.root)

        initNavigation()

        //Song이라는 클래스에 ~~ 변수 선언
        //val song = Song(binding.mainMiniplayerTitleTv.text.toString() , binding.mainMiniplayerSingerTv.text.toString())
        val song = Song("라일락", "아이유",0, 215, false, "music_lilac")

        Log.d("Log test", song.title + song.singer)

        binding.mainPlayerLayout.setOnClickListener() {
            //startActivity(Intent(this, SongActivity::class.java))
            // Intent-activity에서 사용하는 택배 상자(?)[this->SongActivity]

            val intent = Intent(this , SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }

        //initNavigation()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }


    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress=(song.second*1000/song.playTime)
        //mediaPlayer = MediaPlayer.create(this, music)

        if(song.isPlaying){
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }else{
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        }

    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song",null)
        song = if (jsonSong == null){
            Song("라일락", "아이유",0, 215, false, "music_lilac")
        }else{
            gson.fromJson(jsonSong, Song::class.java)
        }
        setMiniPlayer(song)
    }

}

