package com.example.word.ui.theme.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.word.ui.theme.data.Word
import com.example.word.ui.theme.network.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    val wordRepository: WordRepository
): ViewModel() {
    val response: MutableState<List<Word>> = mutableStateOf(listOf())
    fun insert(word: Word){
        viewModelScope.launch {
            wordRepository.insert(word)

        }
    }
    init{
        getAllWords()
    }

    fun getAllWords() {
        viewModelScope.launch {
            wordRepository.getAllWords().catch { e ->
                Log.d("karan", "Exception in getAllWords function in WordViewModel" + e.message)

            }
                .collect() {
                    response.value = it
                }

        }
    }
}