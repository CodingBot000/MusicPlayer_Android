package com.sample.myplayer.domain.repository

import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun getMusicList(): Flow<Resource<List<Music>>>
}