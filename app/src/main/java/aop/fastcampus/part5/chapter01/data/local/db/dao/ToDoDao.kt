package aop.fastcampus.part5.chapter01.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoEntity")
    suspend fun getAll(): List<ToDoEntity>

    @Query("SELECT * FROM ToDoEntity WHERE id=:id")
    suspend fun getById(id: Long): List<ToDoEntity>

    @Insert
    suspend fun insert(toDoEntity: ToDoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntityList: List<ToDoEntity>)

}
