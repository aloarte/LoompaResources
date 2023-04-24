package com.aloarte.loomparesources.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface WillyWonkaDao {

    @Transaction
    @Query("SELECT * FROM oompa_loompa_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getOompaLoompasPaginated(limit: Int, offset: Int): List<OompaLoompaEntity>

    @Transaction
    @Query("SELECT * FROM oompa_loompa_table WHERE id = :employeeId")
    suspend fun getOompaLoompa(employeeId: Int): OompaLoompaEntity?

    @Transaction
    @Upsert
    suspend fun addOompaLoompa(employee: OompaLoompaEntity): Long

    @Transaction
    @Upsert
    suspend fun addOompaLoompas(employees: List<OompaLoompaEntity>) {
        employees.forEach { employee ->
            addOompaLoompa(employee)
        }
    }

    @Transaction
    @Query("SELECT * FROM api_total_table WHERE id = :idTotal")
    suspend fun getApiTotal(idTotal: Int = 1): ApiSizeEntity?

    @Transaction
    @Insert
    suspend fun addApiTotal(apiSize: ApiSizeEntity): Long

}