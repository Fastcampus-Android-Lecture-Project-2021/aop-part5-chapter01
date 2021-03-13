package aop.fastcampus.part5.chapter01.domain.todo

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.data.repository.ToDoRepository
import aop.fastcampus.part5.chapter01.domain.UseCase

internal class GetToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long): ToDoEntity? {
        return toDoRepository.getToDoItem(id)
    }

}
