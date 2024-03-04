package org.fruzitent.mymusictool

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uniffi.spotify.hello

@Composable
fun App() {
  Theme {
    Text(hello())
  }
}
