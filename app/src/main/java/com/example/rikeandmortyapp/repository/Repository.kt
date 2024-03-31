package com.example.rikeandmortyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rikeandmortyapp.data.Character
import com.example.rikeandmortyapp.utils.Resource
import com.example.rikeandmortyapp.data.CartoonApiService
import com.example.rikeandmortyapp.data.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: CartoonApiService
) {
    private val _characters = MutableLiveData<Resource<CharacterResponse>>()
    val characters: LiveData<Resource<CharacterResponse>> get() = _characters

    fun fetchCharacters(): MutableLiveData<Resource<List<Character>>> {
        val data = MutableLiveData<Resource<List<Character>>>()
        data.postValue(Resource.Loading())

        api.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful && response.body() != null && response.code() in 200..300) {
                    response.body()?.let { charactersResponse ->
                        data.value = Resource.Success(charactersResponse.results)
                    }
                } else {
                    data.value = Resource.Error("Error getting characters: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, e: Throwable) {
                data.value = Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        })
        return data
    }

    fun getCharacter(id: Int): LiveData<Resource<Character>> {

        val characterLiveData = MutableLiveData<Resource<Character>>()
        characterLiveData.postValue(Resource.Loading())

        api.getCharacter(id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful && response.body() != null && response.code() in 200..300) {
                    val character = response.body()!!
                    characterLiveData.value = Resource.Success(character)
                } else {
                    val errorMessage = "Error getting character: ${response.code()}"
                    characterLiveData.value = Resource.Error(errorMessage)
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                val errorMessage = t.localizedMessage ?: "Unknown error"
                characterLiveData.value = Resource.Error(errorMessage)
            }
        })

        return characterLiveData
    }

    fun getCharacterUrl(url: String): LiveData<Resource<Character>> {

        val characterLiveData = MutableLiveData<Resource<Character>>()
        characterLiveData.postValue(Resource.Loading())

        api.getSingleCharacter(url).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful && response.body() != null && response.code() in 200..300) {
                    val character = response.body()!!
                    characterLiveData.value = Resource.Success(character)
                } else {
                    val errorMessage = "Error getting character: ${response.code()}"
                    characterLiveData.value = Resource.Error(errorMessage)
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                val errorMessage = t.localizedMessage ?: "Unknown error"
                characterLiveData.value = Resource.Error(errorMessage)
            }
        })

        return characterLiveData
    }


}