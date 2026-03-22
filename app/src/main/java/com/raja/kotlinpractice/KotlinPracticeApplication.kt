package com.raja.kotlinpractice

import android.app.Application
import com.raja.kotlinpractice.di.AppComponent
import com.raja.kotlinpractice.di.AppModule
import com.raja.kotlinpractice.di.DaggerAppComponent

class KotlinPracticeApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
