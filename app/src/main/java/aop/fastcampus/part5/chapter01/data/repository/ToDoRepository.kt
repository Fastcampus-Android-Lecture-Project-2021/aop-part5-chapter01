package aop.fastcampus.part5.chapter01.data.repository

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

}
