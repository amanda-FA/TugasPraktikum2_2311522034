package com.example.shoppinglist.component


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ini halaman Pengaturan",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = { /* TODO: Aksi Pengaturan */ }) {
                Text("Lihat Opsi Lebih Lanjut")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    Setting()
}