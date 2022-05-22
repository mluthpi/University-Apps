package com.example.myuniversity.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "university")
class UnivEntity(
    @PrimaryKey
    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
        )