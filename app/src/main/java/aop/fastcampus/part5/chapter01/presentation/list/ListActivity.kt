package aop.fastcampus.part5.chapter01.presentation.list

import android.os.Bundle
import aop.fastcampus.part5.chapter01.databinding.ActivityListBinding
import aop.fastcampus.part5.chapter01.presentation.BaseActivity
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

internal class ListActivity : BaseActivity<ListViewModel>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityListBinding

    override val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun observeData() {
        viewModel.toDoListLiveData.observe(this) {
            when (val state = it) {
                is ToDoListState.UnInitialized -> {

                }
                is ToDoListState.Loading -> {

                }
                is ToDoListState.Suceess -> {
                    state.toDoList
                }
                is ToDoListState.Error -> {

                }
            }
        }
    }

}
