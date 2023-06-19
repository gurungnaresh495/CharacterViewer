package com.example.simpsonsviewer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsonsviewer.data.ImageInfo
import com.example.simpsonsviewer.data.CharacterInfo
import com.example.simpsonsviewer.repository.CharacterListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterListRepo: CharacterListRepo): ViewModel() {

    private var _characterList = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characterList: StateFlow<List<CharacterInfo>> = _characterList

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characterList.value = characterListRepo.getCharacterList()
            } catch (e: Exception){
                _error.value = e.toString()
            }
        }
    }

}