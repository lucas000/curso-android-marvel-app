package com.example.marvelapp.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.presentation.util.OnCharacterItemClick

class CharactersViewHolder(
    itemCharacterBinding: ItemCharacterBinding,
    private val onItemClick: OnCharacterItemClick
) : RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val textName = itemCharacterBinding.textName
    private val imageCharacter = itemCharacterBinding.imageCharacter

    fun bind(character: Character) {
        textName.text = character.name
        Glide.with(itemView)
            .load(character.imageUrl)
            .fallback(R.drawable.ic_img_loading_error)
            .into(imageCharacter)

        itemView.setOnClickListener {
            onItemClick.invoke(character, imageCharacter)
        }
    }

    companion object {
        fun create(
            parant: ViewGroup,
            onItemClick: (character: Character, view: View) -> Unit
        ): CharactersViewHolder {
            val inflater = LayoutInflater.from(parant.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parant, false)
            return CharactersViewHolder(itemBinding, onItemClick)
        }
    }
}