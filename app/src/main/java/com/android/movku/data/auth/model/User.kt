package com.android.movku.data.auth.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("birth_date")
    var birthDate: String="",
    @SerializedName("address")
    var address: String="",
    @SerializedName("profile_picture")
    var profilePicture: String?="",
)