package rijks.feature.rijksstudio.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.databinding.FragmentRijksstudioDetailBinding
import rijks.feature.rijksstudio.di.DaggerRijksstudioDetailComponent
import javax.inject.Inject


class RijksstudioDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: RijksstudioDetailViewModel
    lateinit var binding: FragmentRijksstudioDetailBinding
    private val args: RijksstudioDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            RijksstudioModuleDependencies::class.java
        )

        DaggerRijksstudioDetailComponent.factory().create(
            coreModuleDependencies,
            requireActivity().application
        )
            .inject(this)


        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rijksstudio_detail,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadArtObjectDetail(args.objectId)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewModel = null
    }

}