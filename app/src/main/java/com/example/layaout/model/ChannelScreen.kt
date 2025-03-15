package com.example.layaout.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChannelScreen(viewModel: ChannelViewModel = hiltViewModel()) {
    val channels by viewModel.filteredChannel.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { viewModel.updateFilter(ChannelFilter.ALL) }) {
                Text(text = "All")
            }

            Button(onClick = { viewModel.updateFilter(ChannelFilter.RECENT) }) {
                Text(text = "Recent")
            }

            Button(onClick = { viewModel.updateFilter(ChannelFilter.ARCHIVED) }) {
                Text(text = "Archived")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(channels) { channel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = channel.name, Modifier.padding(16.dp))
                }
            }
        }
    }
}
