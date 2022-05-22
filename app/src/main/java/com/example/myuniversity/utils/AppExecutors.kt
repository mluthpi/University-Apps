package com.example.myuniversity.utils

import android.os.Looper
import androidx.annotation.MainThread
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
    val networkIO: Executor = Executors.newFixedThreadPool(3)
    val mainThread: Executor = MainThreadExecutors()

    private class MainThreadExecutors : Executor {
        private val mainThreadHandler = android.os.Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}