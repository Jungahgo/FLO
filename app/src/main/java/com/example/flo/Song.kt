package com.example.flo

data class Song(
        var title : String = "", //제목
        var singer : String = "", // 가수
        var playTime : Int = 0, //총 재생시간
        var isPlaying : Boolean = false //재생여부
)
