package aop.fastcampus.part5.chapter01.viewmodel.todo

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.data.repository.TestToDoRepository
import aop.fastcampus.part5.chapter01.data.repository.ToDoRepository
import aop.fastcampus.part5.chapter01.domain.todo.*
import aop.fastcampus.part5.chapter01.presentation.list.ListViewModel
import aop.fastcampus.part5.chapter01.presentation.list.ToDoListState
import aop.fastcampus.part5.chapter01.viewmodel.ViewModelTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private lateinit var viewModel: ListViewModel

    lateinit var toDoRepository: ToDoRepository

    lateinit var getToDoListUseCase: GetToDoListUseCase

    lateinit var getToDoItemUseCase: GetToDoItemUseCase

    lateinit var updateToDoUseCase: UpdateToDoUseCase

    lateinit var insertToDoListUseCase: InsertToDoListUseCase

    lateinit var deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase

    private val list = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    @Before
    fun init() {
        initUseCase()
        initViewModel()
        initData()
    }

    private fun initUseCase() {
        toDoRepository = TestToDoRepository()
        insertToDoListUseCase = InsertToDoListUseCase(toDoRepository)
        getToDoItemUseCase = GetToDoItemUseCase(toDoRepository)
        getToDoListUseCase = GetToDoListUseCase(toDoRepository)
        updateToDoUseCase = UpdateToDoUseCase(toDoRepository)
        deleteAllToDoItemUseCase = DeleteAllToDoItemUseCase(toDoRepository)
    }

    private fun initViewModel() {
        viewModel = ListViewModel(getToDoListUseCase, updateToDoUseCase, deleteAllToDoItemUseCase)
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(list)
    }

    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()

        viewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(list)
            )
        )

    }

    @Test
    fun `test Item update`(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(1)?.hasCompleted ?: false == todo.hasCompleted)
    }

    @Test
    fun `test Item Delete All`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }

}
