package aop.fastcampus.part5.chapter01.presentation.write

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity

sealed class ToDoDetailState {

    object UnInitialized: ToDoDetailState()

    object Loading: ToDoDetailState()

    data class Suceess(
        val toDoItem: ToDoEntity
    ): ToDoDetailState()

    object Delete: ToDoDetailState()

    object Error: ToDoDetailState()

}
