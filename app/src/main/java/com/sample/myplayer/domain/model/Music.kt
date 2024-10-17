package com.sample.myplayer.domain.model


import com.google.gson.annotations.SerializedName

data class Music(
    val id: String,
    val title: String,
    val desc: String,
    val musicUrl: String,
    val albumThumbUrl: String,
) {
    override fun toString(): String {
        return "Music(" +
            "id='$id', " +
            "title='$title', " +
            "desc='$desc', " +
            "musicUrl='$musicUrl', " +
            "albumThumbUrl='$albumThumbUrl'" +
            ")"
    }
}

data class MusicJsonDatas(
    @SerializedName("musics") val datas: List<Music>
)
