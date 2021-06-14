package rijks.feature.rijksstudio.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import rijks.feature.rijksstudio.databinding.ItemLoadingStateBinding


class ArtLoadingStateAdapter() : LoadStateAdapter<ArtLoadingStateAdapter.ArtObjectLoadStateViewHolder>() {

    inner class ArtObjectLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visible(loadState is LoadState.Loading)
        }
    }

    override fun onBindViewHolder(holder: ArtObjectLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ArtObjectLoadStateViewHolder(
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
