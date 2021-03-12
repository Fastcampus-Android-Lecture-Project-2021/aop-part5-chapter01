package aop.fastcampus.part5.chapter01.data.repository

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity

class TestToDoRepository: ToDoRepository {

    lateinit var toDoList: List<ToDoEntity>

    override suspend fun getToDoList(): List<ToDoEntity> {
        return if (::toDoList.isInitialized.not()) {
            listOf()
        } else {
            toDoList
        }
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList = toDoList
    }

}
