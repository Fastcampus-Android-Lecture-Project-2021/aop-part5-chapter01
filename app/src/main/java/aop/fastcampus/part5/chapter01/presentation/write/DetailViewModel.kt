package aop.fastcampus.part5.chapter01.presentation.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.domain.todo.DeleteToDoItemUseCase
import aop.fastcampus.part5.chapter01.domain.todo.GetToDoItemUseCase
import aop.fastcampus.part5.chapter01.domain.todo.UpdateToDoUseCase
import aop.fastcampus.part5.chapter01.presentation.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    private val id: Long,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val toDoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            getToDoItemUseCase(id)?.let {
                _toDoDetailLiveData.postValue(ToDoDetailState.Suceess(it))
            } ?: kotlin.run {
                _toDoDetailLiveData.postValue(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun deleteToDo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            deleteToDoItemUseCase(id)
            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
        } catch (e: Exception) {
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun updateToDo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            getToDoItemUseCase(id)?.let {
                val updateToDoEntity = it.copy(title = title, description = description)
                updateToDoUseCase(updateToDoEntity)
                _toDoDetailLiveData.postValue(ToDoDetailState.Suceess(updateToDoEntity))
            } ?: kotlin.run {
                _toDoDetailLiveData.postValue(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

}
