package com.my.kizzy.feature_api

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.kizzy.common.preference.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val prefs: Prefs,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _apiKey = MutableStateFlow("")
    val apiKey: StateFlow<String> = _apiKey

    private val _isApiEnabled = MutableStateFlow(false)
    val isApiEnabled: StateFlow<Boolean> = _isApiEnabled

    init {
        viewModelScope.launch {
            _apiKey.value = prefs.getString(ApiManager.API_KEY, "") ?: ""
            _isApiEnabled.value = prefs.getBoolean(API_ENABLED, false)
        }
    }

    fun setApiEnabled(enabled: Boolean) {
        viewModelScope.launch {
            prefs.putBoolean(API_ENABLED, enabled)
            _isApiEnabled.value = enabled
            if (enabled) {
                context.startService(Intent(context, ApiService::class.java))
            } else {
                context.stopService(Intent(context, ApiService::class.java))
            }
        }
    }

    companion object {
        const val API_ENABLED = "api_enabled"
    }
}
