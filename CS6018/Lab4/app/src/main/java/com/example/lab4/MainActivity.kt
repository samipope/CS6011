package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jokeViewModel: JokeViewModel by viewModels()

        setContent {
            ChuckNorrisJokesApp(jokeViewModel)
        }
    }
}

@Composable
fun ChuckNorrisJokesApp(jokeViewModel: JokeViewModel = viewModel()) {
    val jokeList by jokeViewModel.jokeList.observeAsState(emptyList())
    val currentJoke by jokeViewModel.currentJoke.observeAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // fetch
        Button(
            onClick = { jokeViewModel.fetchJoke() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch a Chuck Norris Joke")
        }

        Spacer(modifier = Modifier.height(16.dp))

        currentJoke?.let { joke ->
            Text(text = joke.value, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // display the list of previous jokes with the most recent at the top
        LazyColumn {
            items(jokeList.reversed()) { joke ->  // reverse the list
                Text(text = joke.value, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
