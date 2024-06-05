package com.xparkle.githubuserapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.xparkle.githubuserapp.ui.setting.Setting


class SettingViewModel(private val pref: Setting) : ViewModel() {
    fun getThemeSettings(): LiveData<Int> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(themeMode: Int) {
        viewModelScope.launch {
            pref.saveThemeSetting(themeMode)
        }
    }
}