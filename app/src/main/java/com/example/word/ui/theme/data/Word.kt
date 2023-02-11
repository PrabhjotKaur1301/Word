package com.example.word.ui.theme.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordTable")
data class Word(

    val title: String,
    val description: String,



    @PrimaryKey()
    val id: Int? = null

    ) {




}


