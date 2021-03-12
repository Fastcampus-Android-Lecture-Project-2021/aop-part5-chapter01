package aop.fastcampus.part5.chapter01.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.domain.todo.GetToDoListUseCase
import aop.fastcampus.part5.chapter01.domain.todo.InsertToDoListUseCase
import aop.fastcampus.part5.chapter01.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val insertToDoListUseCase: InsertToDoListUseCase
): BaseViewModel() {

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.value = ToDoListState.Loading
        insertToDoListUseCase(
            (0 until 10).map {
                ToDoEntity(
                    id = it.toLong(),
                    title = "title $it",
                    description = "description $it",
                )
            }
        )
        _toDoListLiveData.value = ToDoListState.Suceess(getToDoListUseCase())
    }

}
