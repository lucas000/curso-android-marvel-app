package com.example.marvelapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.DbConstants

@Entity(tableName = DbConstants.FAVORITES_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = DbConstants.FAVORITES_COLUMN_INFO_ID)
    val id: Int,
    @ColumnInfo(name = DbConstants.FAVORITES_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = DbConstants.FAVORITES_COLUMN_INFO_IMAGE)
    val imageUrl: String
)
