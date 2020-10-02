package com.hjk.music_3.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Category")
data class Category (
        @PrimaryKey
        @ColumnInfo(name="id")
        @SerializedName("id")
        var id: Int=0,

        @ColumnInfo(name="bno")
        @SerializedName("bno")
        var bno: Int=0,

        @ColumnInfo(name="parent")
        @SerializedName("parent")
        var parent: Int?=0,

        @ColumnInfo(name="name")
        @SerializedName("name")
        var name: String?=null,

        @ColumnInfo(name="title")
        @SerializedName("title")
        var title: String?=null,

        @ColumnInfo(name="image")
        @SerializedName("image")
        var image: String?=null,

        @ColumnInfo(name="description")
        @SerializedName("description")
        var description: String?=null
)