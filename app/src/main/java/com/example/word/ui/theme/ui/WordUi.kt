package com.example.word.ui.theme.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.word.ui.theme.WordTheme
import com.example.word.ui.theme.data.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordUi: ComponentActivity() {
    val wordViewModel: WordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            WordTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ViewComposable()
                }
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewComposable() {
        val isOpen = remember {
            mutableStateOf(false)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "karan")
                    },
                    )
            },

            floatingActionButton = {
                FloatingActionButton(onClick = {
                    isOpen.value = true


                }) {
                    ShowDialog(isOpen)

                    Icon(imageVector = Icons.Default.Add, contentDescription = "add button ")

                }
            }
        ) {

            RecyclerView(wordViewModel = wordViewModel)

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowDialog(isOpen: MutableState<Boolean>) {

        val title = remember {
            mutableStateOf("")
        }
        val description = remember { mutableStateOf("") }

        if (isOpen.value) {
            AlertDialog(
                onDismissRequest = {
                    isOpen.value = !isOpen.value

                },
                confirmButton = {
                    Button(onClick = {
                        InsertInDialog(title = title, description = description)
                        isOpen.value = false
                    }) {
                        Text(text = "Save")
                    }

                },
                text = {
                    Column() {
                        OutlinedTextField(
                            value = title.value,
                            onValueChange = {
                                title.value = it
                            },
                            label = {
                                Text(text = "enter title")
                            },
                            placeholder = {
                                Text(text = "Enter Title")
                            }
                        )
                        OutlinedTextField(
                            value = description.value,
                            onValueChange = {
                                description.value = it
                            },
                            label = {
                                Text(text = "enter description")
                            },
                            placeholder = {
                                Text(text = "Enter Description")
                            }
                        )
                    }
                }
            )
        }
    }

    private fun InsertInDialog(title: MutableState<String>, description: MutableState<String>) {
        lifecycleScope.launchWhenStarted {
            if (!TextUtils.isEmpty(title.value) && !TextUtils.isEmpty(description.value)) {
                wordViewModel.insert(Word(title.value, description.value))
                Toast.makeText(this@WordUi, "Inserted Sucessfully...", Toast.LENGTH_SHORT)
                    .show()


            } else {
                Toast.makeText(this@WordUi, "Failed...", Toast.LENGTH_SHORT).show()


            }
        }


    }

    @Composable
    fun EachRow(word: Word) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),

            shape = RoundedCornerShape(14.dp),

        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = word.title)
                Text(text = word.description)
            }
        }
        Divider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(), color = Color.Black)



    }

    @Composable
    fun RecyclerView(wordViewModel: WordViewModel) {

        LazyColumn (modifier= Modifier.padding(top= 65.dp)){

            items(wordViewModel.response.value) { word ->
                EachRow(word = word)


            }

        }

    }
}