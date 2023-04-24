package com.aloarte.loomparesources.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OompaLoompaEntity::class, ApiSizeEntity::class], version = 3)
abstract class WillyWonkaDatabase : RoomDatabase() {
    abstract fun willyWonkaDao(): WillyWonkaDao
}
