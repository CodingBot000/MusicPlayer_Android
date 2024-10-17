package com.sample.myplayer.util

import com.sample.myplayer.domain.model.Music

fun convertRunningTime(time: Long): String {
    val minutes = (time / 60000).toInt()
    val seconds = (time % 60000 / 1000).toInt()
    return "${(String.format("%02d", minutes))}:${(String.format("%02d", seconds))}"
}

fun multipleMusicList(list: List<Music>, repeatCount: Int = 5): List<Music> {
    val newList = ArrayList<Music>()
    newList.addAll(list)

    var currentId = newList.maxOfOrNull { it.id.toIntOrNull() ?: 0 } ?: 0

    repeat(repeatCount) {
        for (music in list) {
            currentId += 1
            val newMusic = music.copy(id = currentId.toString())
            newList.add(newMusic)
        }
    }

    return newList
}