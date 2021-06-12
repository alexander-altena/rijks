package rijks.feature.rijksstudio.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.databinding.FragmentArtObjectListBinding
import rijks.feature.rijksstudio.list.di.DaggerRijksstudioListComponent
import javax.inject.Inject


class RijksstudioListFragment : Fragment() {

    @Inject
    lateinit var viewModel: RijksstudioListViewModel
    private lateinit var binding: FragmentArtObjectListBinding
    private lateinit var artObjectsAdapter: RijksstudioListAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_art_object_list,
            container,
            false
        )
        setUpRvWithArtObjectAdapter()
        return binding.root
    }

    private fun setUpRvWithArtObjectAdapter(){
        artObjectsAdapter = RijksstudioListAdapter()
        artObjectsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.rijksstudioRecyclerview.setHasFixedSize(true)
        binding.rijksstudioRecyclerview.adapter = artObjectsAdapter.withLoadStateFooter(
            footer = ArtLoadingStateAdapter()
        )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchArtObjectsFlow()

    }

    private fun launchArtObjectsFlow() {
        lifecycleScope.launch {
            viewModel.getArtObjects().collectLatest { pagedData ->
                artObjectsAdapter.submitData(pagedData)
            }
        }
    }

    override fun onPause() {
//        homeViewModel.setRvPosition(layoutManager.findFirstCompletelyVisibleItemPosition())
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rijksstudioRecyclerview.adapter = null

    }
}