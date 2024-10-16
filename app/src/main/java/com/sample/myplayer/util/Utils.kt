package com.sample.myplayer.util

fun convertRunningTime(time: Long): String {
    val minutes = (time / 60000).toInt()
    val seconds = (time % 60000 / 1000).toInt()
    return "${(String.format("%02d", minutes))}:${(String.format("%02d", seconds))}"
}