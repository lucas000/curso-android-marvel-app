package com.example.core.data.repository

import com.example.core.domain.model.CharacterPaging

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging
}