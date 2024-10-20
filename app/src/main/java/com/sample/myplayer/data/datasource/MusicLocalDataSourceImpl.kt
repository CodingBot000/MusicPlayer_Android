package com.sample.myplayer.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.sample.myplayer.domain.datasource.MusicLocalDataSource
import com.sample.myplayer.domain.model.MusicJsonDatas
import com.sample.myplayer.state.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MusicLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : MusicLocalDataSource
{
    override suspend fun getMusicList() =
        flow {
            try {
                val jsonString = context.assets.open("music_info_json.json")
                    .bufferedReader()
                    .use { it.readText() }
                val musicList = gson.fromJson(jsonString, MusicJsonDatas::class.java).datas

                if (musicList.isEmpty()) {
                    emit(Resource.Error(message = "parsing result List is Empty"))
                    return@flow
                }
                emit(Resource.Success(musicList))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }

    }
}