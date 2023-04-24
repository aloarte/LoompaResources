package com.aloarte.loomparesources.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "oompa_loompa_table",
)
data class OompaLoompaEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val color: String,
    val food: String,
    val randomString: String,
    val song: String,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val quote: String? = null,
    val description: String? = null
)

