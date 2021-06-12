package rijks.feature.rijksstudio.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
        print("the item is ${item?.title}")
        item?.let { holder.bindArtObject(it) }

    }


    inner class ArtObjectViewHolder(private val binding: ItemArtObjectListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindArtObject(item: ArtObject) = with(binding) {
//            rijksstudioItemImg.load(item.imageUrl)
            rijksstudioItemTitle.text = item.title
//            val directions = ArtObjectListFragmentDirections.actionArtObjectListFragmentToArtObjectDetailFragment(item.objectNumber)
//            root.setOnClickListener {
//                it.findNavController().navigate(directions)
//            }
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