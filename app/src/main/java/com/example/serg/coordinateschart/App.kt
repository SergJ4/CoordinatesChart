package com.example.serg.coordinateschart

import android.app.Activity
import android.app.Application
import com.example.repository.di.DaggerRepoComponent
import com.example.serg.coordinateschart.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        inject()
    }

    private fun inject() {
        DaggerAppComponent
            .builder()
            .app(this)
            .repoComponent(
                DaggerRepoComponent
                    .builder()
                    .appContext(this)
                    .build()
            )
            .build()
            .inject(this)
    }
}