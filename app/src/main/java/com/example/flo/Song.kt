package com.example.flo

data class Song(
        var title : String = "", //제목
        var singer : String = "", // 가수
        var second : Int = 0, //몇 초까지 재생되었는지
        var playTime : Int = 0, //총 재생시간
        var isPlaying : Boolean = false, //재생여부
        var music : String = "" // music
)
