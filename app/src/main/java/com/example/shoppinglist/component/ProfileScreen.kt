package com.example.shoppinglist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PROFIL SAYA") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.my_photo),
                    contentDescription = "Foto Diri",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ProfileItem("Nama:", "Amanda Fitri Abdillah")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileItem("NIM:", "2311522034")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileItem("Hobi:", "Main Roblox")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileItem("TTL:", "Padang, 8 November 2005")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileItem("Mata Kuliah:", "PTB")
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}