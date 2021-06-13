package rijks.feature.rijksstudio.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rijks.domain.model.ArtObject
import rijks.feature.rijksstudio.databinding.ItemArtObjectListBinding

class RijksstudioListAdapter : PagingDataAdapter<ArtObject, RijksstudioListAdapter.ArtObjectViewHolder>(
    ArtObjectComparator
) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtObjectViewHolder {
       return ArtObjectViewHolder(ItemArtObjectListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindArtObject(it) }

    }


    inner class ArtObjectViewHolder(private val binding: ItemArtObjectListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindArtObject(item: ArtObject) = with(binding) {
            rijksstudioItemImg.load(item.imageUrl)
            rijksstudioItemTitle.text = item.title
            val directions = RijksstudioListFragmentDirections.showArtObjectDetail(item.objectNumber)
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