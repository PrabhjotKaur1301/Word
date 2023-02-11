package com.example.word.ui.theme.network

import com.example.word.ui.theme.data.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val dao: WordDao
) {
    suspend fun insert(word: Word)= withContext(Dispatchers.IO){
        dao.insert(word)
    }

    fun getAllWords() : Flow<List<Word>> = dao.getAllWords()

}