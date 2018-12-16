package com.ovrbach.volvomobility.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ovrbach.common.entities.AlbumSimplified
import com.ovrbach.volvomobility.NavigationListener
import com.ovrbach.volvomobility.OnItemClickListener
import com.ovrbach.volvomobility.OnItemViewClickedListener
import com.ovrbach.volvomobility.R
import com.ovrbach.volvomobility.databinding.ItemAlbumBinding
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(
        val navigationListener: NavigationListener
) : ListAdapter<AlbumSimplified,
        AlbumsAdapter.Holder>(
        object : DiffUtil.ItemCallback<AlbumSimplified>() {

            override fun areItemsTheSame(oldItem: AlbumSimplified, newItem: AlbumSimplified): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AlbumSimplified, newItem: AlbumSimplified): Boolean =
                    oldItem == newItem

        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(
                DataBindingUtil.inflate(inflater, R.layout.item_album, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            navigationListener.showAlbumDetails(item.id, holder.binding.itemAlbumImage)
        }
    }

    class Holder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlbumSimplified) {
            binding.item = item
        }
    }
}