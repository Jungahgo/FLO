package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
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
    private val song:Song = Song()
    private lateinit var player:Player
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //activity 최상뷰를 마음대로 쓸거야

        initSong()

        player=Player(song.playTime,song.isPlaying)
        player.start()
        //player.interrupt()

//        if(intent.hasExtra("title") && intent.hasExtra("singer")){
//            binding.songNameTv.text = intent.getStringExtra("title")
//            binding.songSingerNameTv.text = intent.getStringExtra("singer")
//
//        }

        binding.songNuguBtnDownIv.setOnClickListener{//songNuguBtnDown을 눌렀을 때 나올 작업
            //->activity를 끄는 작업을 적을 것임
            finish()
        }

        //재생, 정지
        binding.songMiniplayerPlayIv.setOnClickListener{
            player.isPlaying=true
            setPlayerStatus(false)
        }

        binding.songMiniplayerMvpauseIv.setOnClickListener{
            player.isPlaying=false
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

    private fun initSong(){
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("playTime") && intent.hasExtra("isPlaying")){
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.playTime = intent.getIntExtra("playTime",0)
            song.isPlaying = intent.getBooleanExtra("isplaying",false)

            binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime % 60)
            binding.songNameTv.text = song.title
            binding.songSingerNameTv.text = song.singer
            setPlayerStatus(song.isPlaying)
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

    inner class Player(private val playTime:Int,var isPlaying: Boolean) : Thread(){
        private var second = 0

        override fun run() {
            try{
                while(true){
                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){

                        sleep(1000)

                        second++

                        runOnUiThread(){
                            binding.songTimeV.progress=second*1000/playTime
                            binding.songStartTimeTv.text = String.format("%02d:%02d",second/60, second%60)
                        }
                    }
                }
            }catch (e:InterruptedException){
                Log.d("interrupt", "쓰레드가 종료되었습니다.")
            }

        }
    }

    override fun onDestroy() {
        player.interrupt()
        super.onDestroy()
    }

}
