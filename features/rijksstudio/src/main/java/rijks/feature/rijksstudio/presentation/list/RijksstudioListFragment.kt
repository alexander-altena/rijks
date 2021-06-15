package rijks.feature.rijksstudio.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.paging.LoadState
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.databinding.FragmentRijksstudioListBinding
import rijks.feature.rijksstudio.di.DaggerRijksstudioListComponent
import javax.inject.Inject


class RijksstudioListFragment : Fragment() {

    @Inject
    lateinit var viewModel: RijksstudioListViewModel
    private lateinit var binding: FragmentRijksstudioListBinding
    private val artObjectsAdapter = RijksstudioListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            RijksstudioModuleDependencies::class.java
        )

        DaggerRijksstudioListComponent.factory().create(
            coreModuleDependencies,
            requireActivity().application
        )
            .inject(this)

        super.onCreate(savedInstanceState)

    }

    init { // Notice that we can safely launch in the constructor of the Fragment.
        lifecycleScope.launch {
            whenStarted {
                showArtObjects()
            }

        }
    }

    private suspend fun showArtObjects() {
        viewModel.getArtObjects().collectLatest {
            artObjectsAdapter.submitData(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rijksstudio_list,
            container,
            false
        )

        setUpRvWithArtObjectAdapter()
        return binding.root
    }

    private fun setUpRvWithArtObjectAdapter() {
        binding.rijksstudioRecyclerview.apply {
            adapter = artObjectsAdapter.withLoadStateFooter(footer = ArtLoadingStateAdapter())
        }

        artObjectsAdapter.addLoadStateListener { loadState ->
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
