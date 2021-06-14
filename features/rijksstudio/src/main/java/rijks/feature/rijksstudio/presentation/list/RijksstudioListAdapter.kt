package rijks.feature.rijksstudio.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rijks.domain.model.ArtObject
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.BR

private const val NORMAL: Int = R.layout.item_art_object_list
private const val PORTRAIT: Int = R.layout.item_art_object_list_portrait
private const val HEADER: Int = R.layout.item_art_object_header

class RijksstudioListAdapter :
    PagingDataAdapter<ArtObject, RijksstudioListAdapter.ArtObjectViewHolder>(
        ArtObjectComparator
    ) {


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item == null || item.imgRatio < 0.7) {
            println(" the image ratio is ${item?.imgRatio}")
            return NORMAL
        }
        return PORTRAIT
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtObjectViewHolder {
        return ArtObjectViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindArtObject(it) }

    }


    inner class ArtObjectViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindArtObject(item: ArtObject) = with(binding) {
            setVariable(BR.artObject, item)
            executePendingBindings()
            val directions =
                RijksstudioListFragmentDirections.showArtObjectDetail(item.objectNumber)
            root.setOnClickListener {
                it.findNavController().navigate(directions)
            }
        }

    }

    companion object ArtObjectComparator : DiffUtil.ItemCallback<ArtObject>() {
        override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem == newItem
        }
    }
}