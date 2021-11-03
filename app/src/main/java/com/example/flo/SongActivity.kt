package com.example.flo

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.util.*
import kotlin.concurrent.timer

//AppCompatActivity - Andriod에서 Activity 기능을 사용할 수 있게 함

class SongActivity : AppCompatActivity() {

    //변수선언 방법: var - 선언 이후 값 변경 가능 / val - 선언 이후 값 변경 불가능
    //vat test1 : Int = 2

    //lateinit: 늦은 초기화 (var이랑만 쓸 수 있음)
    //binding 객체 선언
    lateinit var binding : ActivitySongBinding

    private val song:Song = Song()

    //미디어 플레이어
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var timer:Timer
    private val handler = Handler(Looper.getMainLooper())

    //Gson
    private var gson: Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //바인딩 초기화
        binding = ActivitySongBinding.inflate(layoutInflater)
        // 레이아웃(root뷰) 표시 (activity 최상뷰를 마음대로 쓸거야)
        setContentView(binding.root)

        initSong()

        timer=Timer(song.playTime,song.isPlaying)
        timer.start()


        //뷰 바인딩으로 버튼접근
        binding.songNuguBtnDownIv.setOnClickListener{
            //songNuguBtnDown을 눌렀을 때 나올 작업
            //->activity를 끄는 작업을 적을 것임
            finish()
        }

        //재생, 정지
        binding.songMiniplayerPlayIv.setOnClickListener{
            timer.isPlaying=true
            setPlayerStatus(true)
            song.isPlaying = true
            //mediaPlayer ?= null로 위에서 정의해줘서 계속 ? 붙여야함
            mediaPlayer?.start()
        }

        binding.songMiniplayerMvpauseIv.setOnClickListener{
            timer.isPlaying = false
            setPlayerStatus(false)
            song.isPlaying = false
            mediaPlayer?.pause()
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
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("second") && intent.hasExtra("playTime") && intent.hasExtra("isPlaying")&& intent.hasExtra("music") ){
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.second = intent.getIntExtra("second",0)
            song.playTime = intent.getIntExtra("playTime",0)
            song.isPlaying = intent.getBooleanExtra("isplaying",false)
            song.music = intent.getStringExtra("music")!!
            //resources의 이름으로 resource찾음
            val music = resources.getIdentifier(song.music, "raw", this.packageName)

            binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime % 60)
            binding.songNameTv.text = song.title
            binding.songSingerNameTv.text = song.singer
            setPlayerStatus(song.isPlaying)
            //mediaPlayer 연동
            mediaPlayer = MediaPlayer.create(this, music)
        }
    }


    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerPlayIv.visibility = View.GONE
            binding.songMiniplayerMvpauseIv.visibility = View.VISIBLE
        }else{
            binding.songMiniplayerPlayIv.visibility = View.VISIBLE
            binding.songMiniplayerMvpauseIv.visibility = View.GONE
        }
    }

    inner class Timer(private val playTime:Int=0,var isPlaying: Boolean = false) : Thread(){
        private var second:Long  = 0
//        @SuppressLint("SetTextI18n")
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
                            binding.songTimeV.progress= ((second * 1000 / playTime).toInt())
                            binding.songStartTimeTv.text = String.format("%02d:%02d",second/60, second%60)
                        }
                    }
                }
            }catch (e:InterruptedException){
                Log.d("SONG", "쓰레드가 종료되었습니다. ${e.message}")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        timer.isPlaying = false // 스레드 중지
        song.isPlaying = false
        song.second = (binding.songTimeV.progress*song.playTime)/1000
        setPlayerStatus(false)//재생과 일시정지가 바뀌는 함수

        //앱 나갔다들어왔을 때 노래 얼마동안 재생되었었는지 기억 (간단한 데이터)
        val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
        //sharedPreferences 조작할 때 사용을 한다.
        val editor = sharedPreferences.edit()
        //editor.putString("title", song.title)
        //위와 같이하면 번거로움 -> 해결방안: JSON(데이터 표현 표준)(자바 객체 다른 곳으로 전송)
        //근데 바로 전송 못함 -> GSON 필요(객체->JSON/JSON->객체)

        //Gson->Json
        val json = gson.toJson(song)

        //putString(한줄직렬)
        editor.putString("song", json)

        editor.apply()
    }

    override fun onDestroy() {

        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어플레이어 해제
    }

}
