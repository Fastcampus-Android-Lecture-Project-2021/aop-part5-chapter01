package aop.fastcampus.part5.chapter01.data.repository

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun getToDoItem(id: Long): ToDoEntity?

    suspend fun insertToDoItem(toDoEntity: ToDoEntity): Long

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoEntity: ToDoEntity)

    suspend fun deleteToDoItem(id: Long)

    suspend fun deleteAll()

}
