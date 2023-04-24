package com.aloarte.loomparesources.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "api_total_table",
)
data class ApiSizeEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val count: Int
)

