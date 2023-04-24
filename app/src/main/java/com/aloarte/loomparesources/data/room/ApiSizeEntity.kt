package com.aloarte.loomparesources.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "api_total_table",
)
data class ApiSizeEntity(
    @PrimaryKey
    val count: Int
)

