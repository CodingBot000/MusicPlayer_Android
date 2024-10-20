package com.sample.myplayer.data.datasource

import com.sample.myplayer.domain.datasource.MusicDataSource
import com.sample.myplayer.domain.model.MusicJsonDatas
import com.sample.myplayer.state.Resource
import com.sample.myplayer.util.Constants
import com.sample.myplayer.util.GsonObject
import com.sample.myplayer.util.JsonReaderApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MusicDataSourceImpl @Inject constructor(
    private val jsonReaderApi: JsonReaderApi
) : MusicDataSource
{
    override suspend fun getMusicList() =
        flow {
            try {
                val jsonString = jsonReaderApi.jsonUrlReader(
                    Constants.MUSIC_LIST_API
                )
                val musicList =
                    GsonObject.gson.fromJson(jsonString, MusicJsonDatas::class.java).datas
                if (musicList.isEmpty()) {
                    emit(Resource.Error(message = "parsing result List is Empty"))
                    return@flow
                }

                if (musicList.isNotEmpty())
                    emit(Resource.Success(musicList))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }

}