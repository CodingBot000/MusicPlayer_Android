package com.sample.myplayer.data.repository

import com.sample.myplayer.domain.datasource.MusicDataSource
import com.sample.myplayer.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDataSource: MusicDataSource,
) : MusicRepository
{
    override suspend fun getMusicList() =  musicDataSource.getMusicList()
}
