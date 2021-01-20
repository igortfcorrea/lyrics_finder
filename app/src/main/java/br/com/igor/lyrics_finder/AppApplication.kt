package br.com.igor.lyrics_finder

import android.app.Application
import br.com.igor.lyrics_finder.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(apiModule)
        }
    }
}