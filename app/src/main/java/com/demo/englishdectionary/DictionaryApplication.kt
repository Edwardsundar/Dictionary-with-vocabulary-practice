package com.demo.englishdectionary

import android.app.Application
import com.demo.englishdectionary.common.CharlesKOgden
import com.demo.englishdectionary.di.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class DictionaryApplication: Application()
