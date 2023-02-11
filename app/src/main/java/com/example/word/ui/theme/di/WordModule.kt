package com.example.word.ui.theme.di

import android.app.Application
import androidx.room.Room
import com.example.word.ui.theme.network.WordDao
import com.example.word.ui.theme.network.WordDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        WordDataBase::class.java,
        "WordDataBase"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun proivesDao(wordDataBase: WordDataBase): WordDao = wordDataBase.getDao()
}