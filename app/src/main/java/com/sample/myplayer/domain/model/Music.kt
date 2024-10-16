package com.sample.myplayer.domain.model


import com.google.gson.annotations.SerializedName

data class Music(
    val id: String,
    val title: String,
    val desc: String,
    val musicUrl: String,
    val albumThumbUrl: String
)

data class MusicJsonDatas(
    @SerializedName("musics") val datas: List<Music>
)
