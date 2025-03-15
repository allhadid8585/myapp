package com.example.layaout.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class ChannelRepository @Inject constructor(){
    private val channels = listOf(
        Channel(id = 1,  name = "akhbor", isResent = true, isArchived = false),
        Channel(id = 2, name = "varzesh", isResent = false, isArchived = true),
        Channel(id = 3, name = "kino", isResent = true, isArchived = false),
        Channel(id = 4, name = "animation", isResent = false, isArchived = false)
    )

    private val channelsFlow = MutableStateFlow(channels)

    fun getAllChannels(): Flow<List<Channel>> = channelsFlow

}
