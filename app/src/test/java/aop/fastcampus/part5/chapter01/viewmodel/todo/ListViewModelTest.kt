package aop.fastcampus.part5.chapter01.viewmodel.todo

import aop.fastcampus.part5.chapter01.data.entity.ToDoEntity
import aop.fastcampus.part5.chapter01.domain.todo.GetToDoListUseCase
import aop.fastcampus.part5.chapter01.domain.todo.InsertToDoListUseCase
import aop.fastcampus.part5.chapter01.presentation.list.ListViewModel
import aop.fastcampus.part5.chapter01.presentation.list.ToDoListState
import aop.fastcampus.part5.chapter01.viewmodel.ViewModelTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    private lateinit var viewModel: ListViewModel

    @Mock
    lateinit var getToDoListUseCase: GetToDoListUseCase

    @Mock
    lateinit var insertToDoListUseCase: InsertToDoListUseCase

    @Before
    fun initViewModel() {
        viewModel = ListViewModel(
            getToDoListUseCase,
            insertToDoListUseCase
        )
    }

    @Test
    fun `test viewModel state`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()

        val list = (0 until 10).map {
            ToDoEntity(
                id = it.toLong(),
                title = "title $it",
                description = "description $it",
            )
        }

        mocking(getToDoListUseCase()).thenAnswer { list }

        viewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(list)
            )
        )

    }

}
