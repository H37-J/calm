package com.hjk.music_3.data.local.model

import com.google.gson.annotations.SerializedName
import java.util.Objects
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Yassin Ajdi.
 */
@Entity(tableName = "BackGround")
data class BackGround (

        @PrimaryKey
        @ColumnInfo(name = "bno")
        @SerializedName("bno")
        var bno: Long = 0,

        @SerializedName("image")
        var image: String? = null
)
