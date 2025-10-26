package com.my.kizzy.feature_api

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ApiScreen(
    viewModel: ApiViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val apiKey by viewModel.apiKey.collectAsState()
    val isApiEnabled by viewModel.isApiEnabled.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Enable API Server")
            Switch(
                checked = isApiEnabled,
                onCheckedChange = { viewModel.setApiEnabled(it) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "API Key:")
        TextField(
            value = apiKey,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { clipboardManager.setText(AnnotatedString(apiKey)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Copy API Key")
        }
    }
}
