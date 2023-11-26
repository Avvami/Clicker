package com.personal.clicker.di

import android.content.Context
import com.personal.clicker.data.AppDatabase
import com.personal.clicker.data.repository.ClickerRepositoryImpl
import com.personal.clicker.domain.repository.ClickerRepository

interface AppModule {
    val clickerRepository: ClickerRepository
}

class AppModuleImpl(private val context: Context): AppModule {
    override val clickerRepository: ClickerRepository by lazy {
        ClickerRepositoryImpl(AppDatabase.getDatabase(context).dao)
    }
}