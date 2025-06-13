package com.example.profilesearchapp.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.profilesearchapp.model.Owner
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "repositories")
data class GHRepo(
    @PrimaryKey val id: Long,
    val name: String,

    @SerializedName("html_url")
    val repoURL: String,

    @Embedded(prefix = "owner_")
    val owner: Owner
) : Serializable
