package aop.fastcampus.part5.chapter01.domain.todo

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.data.repository.ToDoRepository
import aop.fastcampus.part5.chapter01.domain.UseCase

internal class UpdateToDoUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(toDoEntity: ToDoEntity) {
        return toDoRepository.updateToDoItem(toDoEntity)
    }

}
