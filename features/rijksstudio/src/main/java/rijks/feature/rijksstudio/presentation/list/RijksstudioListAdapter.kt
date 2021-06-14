package rijks.feature.rijksstudio.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rijks.domain.model.ArtObjectList
import rijks.feature.rijksstudio.BR
import rijks.feature.rijksstudio.R
import rijks.feature.rijksstudio.databinding.ItemArtObjectHeaderBinding

private const val LANDSCAPE: Int = R.layout.item_art_object_list
private const val PORTRAIT: Int = R.layout.item_art_object_list_portrait
private const val HEADER: Int = R.layout.item_art_object_header

class RijksstudioListAdapter :
    PagingDataAdapter<ArtObjectList, RecyclerView.ViewHolder>(
        ArtObjectDiffCallBack()
    ) {


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is ArtObjectList.ArtObjectHeader) {
            return HEADER
        }
        return getArtObjectViewType(item as ArtObjectList.ArtObjectItem)
    }


    private fun getArtObjectViewType(artObject: ArtObjectList.ArtObjectItem): Int {
        return if (artObject.art.imgRatio < 0.7) {
            LANDSCAPE
        } else {
            PORTRAIT
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LANDSCAPE -> ArtObjectViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    viewType,
                    parent,
                    false
                )
            )
            PORTRAIT -> ArtObjectViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    viewType,
                    parent,
                    false
                )
            )
            else -> HeaderViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    viewType,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            val item = getItem(position)
            if (holder is ArtObjectViewHolder) {
                holder.bindArtObject(item as ArtObjectList.ArtObjectItem)
            } else if (holder is HeaderViewHolder) {
                holder.bindHeader(item as ArtObjectList.ArtObjectHeader)
            }

        }
    }

    inner class ArtObjectViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindArtObject(item: ArtObjectList.ArtObjectItem) = with(binding) {
            setVariable(BR.artObject, item.art)
            executePendingBindings()
            val directions =
                RijksstudioListFragmentDirections.showArtObjectDetail(item.art.objectNumber)
            root.setOnClickListener {
                it.findNavController().navigate(directions)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemArtObjectHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindHeader(item: ArtObjectList.ArtObjectHeader) {
            binding.artObjectHeaderTv.text = item.header
        }
    }


    private class ArtObjectDiffCallBack : DiffUtil.ItemCallback<ArtObjectList>() {

        override fun areItemsTheSame(oldItem: ArtObjectList, newItem: ArtObjectList): Boolean {
            return (
                    oldItem is ArtObjectList.ArtObjectItem && newItem is ArtObjectList.ArtObjectItem &&
                            oldItem.art.objectNumber == newItem.art.objectNumber
                    ) || (
                    oldItem is ArtObjectList.ArtObjectHeader && newItem is ArtObjectList.ArtObjectHeader &&
                            oldItem.header == newItem.header
                    )
        }

        override fun areContentsTheSame(oldItem: ArtObjectList, newItem: ArtObjectList): Boolean {
            return oldItem == newItem
        }
    }
}