package dev.marcosfarias.pokedex

import android.app.Application
import dev.marcosfarias.pokedex.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Тестовый Application класс для инициализации Koin DI в тестах
 */
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    fun initKoin() {
        stopKoin() // Останавливаем любой предыдущий экземпляр Koin
        startKoin {
            androidContext(this@TestApplication)
            modules(appComponent)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}
