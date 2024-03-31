package com.example.rikeandmortyapp.ui.characters.adapter

import androidx.lifecycle.LiveData
import com.example.rikeandmortyapp.data.Character
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rikeandmortyapp.utils.Resource
import com.example.rikeandmortyapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _characters = MutableLiveData<Resource<List<Character>>>()
    val characters: LiveData<Resource<List<Character>>> = _characters

    fun giveCharacters(): LiveData<Resource<List<Character>>> = repository.fetchCharacters()

}