package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.Character
import com.example.marvelapp.framework.paging.CharactersPageSource
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor (
    private val remoteDataSource: CharactersRemoteDataSource
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPageSource(remoteDataSource, query)
    }
}