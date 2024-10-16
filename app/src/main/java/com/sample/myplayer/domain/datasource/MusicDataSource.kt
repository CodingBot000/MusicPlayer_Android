package com.sample.myplayer.domain.datasource

import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.Resource
import kotlinx.coroutines.flow.Flow

interface MusicDataSource {
    suspend fun getMusicList(): Flow<Resource<List<Music>>>

}