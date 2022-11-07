package com.example.marvelapp.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.core.domain.model.Character
import com.example.marvelapp.R

class CharactersViewHolder(
    itemCharacterBinding: ItemCharacterBinding
) : RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val textName = itemCharacterBinding.textName
    private val imageCharacter = itemCharacterBinding.imageCharacter

    fun bind(character: Character) {
        textName.text = character.name
        Glide.with(itemView)
            .load(character.imageUrl)
            .fallback(R.drawable.ic_img_loading_error)
            .into(imageCharacter)
    }

    companion object {
        fun create(parant: ViewGroup): CharactersViewHolder {
            val inflater = LayoutInflater.from(parant.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parant, false)
            return CharactersViewHolder(itemBinding)
        }
    }
}