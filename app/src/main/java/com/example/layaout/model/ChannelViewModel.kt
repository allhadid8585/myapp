package com.example.layaout.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val repository: ChannelRepository,
    private val sivedState: SavedStateHandle
): ViewModel(){

    companion object{
        private const val FILTER_STATE_KEY = "FilterSelectedType"

    }

    private val selectedFilter: StateFlow<ChannelFilter> = sivedState.getStateFlow(
        FILTER_STATE_KEY,
        ChannelFilter.ALL
    )

    val filteredChannel: Flow<List<Channel>> = combine(
        repository.getAllChannels(),
        selectedFilter
    ){channelList, filterType ->
        applyFilter (channelList, filterType)
    }.onStart { emit(emptyList()) }

    fun updateFilter (newFilter: ChannelFilter){
        sivedState[FILTER_STATE_KEY] = newFilter
    }

    private fun applyFilter(channels: List<Channel>, filter: ChannelFilter): List<Channel>{
        return when(filter){
            ChannelFilter.ALL -> channels
            ChannelFilter.ARCHIVED -> channels.filter { it.isArchived }
            ChannelFilter.RECENT -> channels.filter { it.isResent }
        }
    }


}

