package com.hjk.music_3.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class User (

        @PrimaryKey
        @ColumnInfo(name="bno")
        @SerializedName("bno")
        var bno: Long=0,

        @ColumnInfo(name="id")
        @SerializedName("id")
        var id: String?=null,

        @ColumnInfo(name="pwd")
        @SerializedName("pwd")
        var pwd: String?=null,

        @ColumnInfo(name="name")
        @SerializedName("name")
        var name: String?=null,

        @ColumnInfo(name="image")
        @SerializedName("image")
        var image: String? =null,

        @ColumnInfo(name="save_music")
        @SerializedName("save_music")
        var save_music: Long=0,

        @ColumnInfo(name="save_back")
        @SerializedName("save_back")
        var save_back: String="0",

        @ColumnInfo(name="save_login")
        @SerializedName("save_login")
        var save_login: String="0",

        @ColumnInfo(name="save_day")
        @SerializedName("save_day")
        var save_day: String? ="0",

        @ColumnInfo(name="save_history")
        @SerializedName("save_history")
        var save_history: String? ="0",

        @ColumnInfo(name="save_time")
        @SerializedName("save_time")
        var save_time: String? ="0",

        @ColumnInfo(name="like_music")
        @SerializedName("like_music")
        var like_music: String? ="0",

        @ColumnInfo(name="last_login")
        @SerializedName("last_login")
        var last_login: String? ="0",

        @ColumnInfo(name="subscription_status")
        @SerializedName("subscription_status")
        var subscription_status: String? ="0",

        @ColumnInfo(name="subscription_start_date")
        @SerializedName("subscription_start_date")
        var subscription_start_date: String? ="0"
)
