package rijks.feature.rijksstudio.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.databinding.FragmentRijksstudioListBinding
import rijks.feature.rijksstudio.di.DaggerRijksstudioListComponent
import javax.inject.Inject


class RijksstudioListFragment : Fragment() {

    @Inject
    lateinit var viewModel: RijksstudioListViewModel
    private lateinit var binding: FragmentRijksstudioListBinding
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
            R.layout.fragment_rijksstudio_list,
            container,
            false
        )
        setUpRvWithArtObjectAdapter()
        return binding.root
    }

    private fun setUpRvWithArtObjectAdapter(){
        artObjectsAdapter = RijksstudioListAdapter()
        binding.rijksstudioRecyclerview.apply {
            setHasFixedSize(true)
            adapter = artObjectsAdapter.withLoadStateFooter(footer = ArtLoadingStateAdapter())
            artObjectsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.getArtObjects().collect {
                artObjectsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rijksstudioRecyclerview.adapter = null

    }
}