package aop.fastcampus.part5.chapter01.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.domain.todo.GetToDoItemUseCase
import aop.fastcampus.part5.chapter01.domain.todo.GetToDoListUseCase
import aop.fastcampus.part5.chapter01.domain.todo.UpdateToDoUseCase
import aop.fastcampus.part5.chapter01.presentation.BaseViewModel
import kotlinx.coroutines.*

internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
): BaseViewModel() {

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Suceess(getToDoListUseCase()))
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
        updateToDoUseCase(toDoEntity)
    }

}
