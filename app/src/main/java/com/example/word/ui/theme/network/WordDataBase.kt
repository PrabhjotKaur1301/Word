package com.example.word.ui.theme.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.word.ui.theme.data.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDataBase: RoomDatabase() {
    abstract fun getDao(): WordDao
}