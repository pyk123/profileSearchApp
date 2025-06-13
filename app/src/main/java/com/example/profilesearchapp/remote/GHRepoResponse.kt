package com.example.profilesearchapp.remote

import com.example.profilesearchapp.local.GHRepo
import com.google.gson.annotations.SerializedName

data class GHRepoResponse(
    @SerializedName("items")
    val items: List<GHRepo>
)
