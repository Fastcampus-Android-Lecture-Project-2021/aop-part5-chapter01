package aop.fastcampus.part5.chapter01.viewmodel.todo

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.data.repository.TestToDoRepository
import aop.fastcampus.part5.chapter01.data.repository.ToDoRepository
import aop.fastcampus.part5.chapter01.domain.todo.*
import aop.fastcampus.part5.chapter01.presentation.detail.DetailMode
import aop.fastcampus.part5.chapter01.presentation.list.ListViewModel
import aop.fastcampus.part5.chapter01.presentation.list.ToDoListState
import aop.fastcampus.part5.chapter01.presentation.detail.DetailViewModel
import aop.fastcampus.part5.chapter01.presentation.detail.ToDoDetailState
import aop.fastcampus.part5.chapter01.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class DetailViewModelTest : ViewModelTest() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var listViewModel: ListViewModel

    lateinit var toDoRepository: ToDoRepository

    lateinit var getToDoListUseCase: GetToDoListUseCase

    lateinit var getToDoItemUseCase: GetToDoItemUseCase

    lateinit var insertToDoUseCase: InsertToDoUseCase

    lateinit var updateToDoUseCase: UpdateToDoUseCase

    lateinit var deleteToDoItemUseCase: DeleteToDoItemUseCase

    @Mock
    lateinit var deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase

    val id = 1L

    private val todo = ToDoEntity(
        id,
        title = "title 1",
        description = "description 1",
        hasCompleted = false
    )

    @Before
    fun init() {
        initUseCase()
        initViewModel()
        initData()
    }

    private fun initUseCase() {
        toDoRepository = TestToDoRepository()
        getToDoItemUseCase = GetToDoItemUseCase(toDoRepository)
        getToDoListUseCase = GetToDoListUseCase(toDoRepository)
        insertToDoUseCase = InsertToDoUseCase(toDoRepository)
        getToDoItemUseCase = GetToDoItemUseCase(toDoRepository)
        updateToDoUseCase = UpdateToDoUseCase(toDoRepository)
        deleteToDoItemUseCase = DeleteToDoItemUseCase(toDoRepository)
    }

    private fun initViewModel() {
        detailViewModel = DetailViewModel(DetailMode.DETAIL, id, getToDoItemUseCase, deleteToDoItemUseCase, updateToDoUseCase, insertToDoUseCase)
        listViewModel = ListViewModel(getToDoListUseCase, updateToDoUseCase, deleteAllToDoItemUseCase)
    }

    private fun initData() = runBlockingTest {
        insertToDoUseCase(todo)
    }

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Suceess(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.deleteToDo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        val listTestObservable = listViewModel.toDoListLiveData.test()

        listViewModel.fetchData()

        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }

    @Test
    fun `test update todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        val updateTitle = "title 1 update"
        val updateDescription = "description 1 update"

        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )

        detailViewModel.writeToDo(
            title = updateTitle,
            description = updateDescription
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Suceess(updateToDo)
            )
        )
    }

}
