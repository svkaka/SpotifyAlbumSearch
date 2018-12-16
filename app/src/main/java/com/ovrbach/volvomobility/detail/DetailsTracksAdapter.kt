package com.ovrbach.volvomobility.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ovrbach.common.entities.TrackSimplified
import com.ovrbach.volvomobility.R
import com.ovrbach.volvomobility.databinding.ItemTrackBinding

class DetailsTracksAdapter : ListAdapter<TrackSimplified, DetailsTracksAdapter.Holder>(object : DiffUtil.ItemCallback<TrackSimplified>() {

    override fun areItemsTheSame(oldItem: TrackSimplified, newItem: TrackSimplified): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TrackSimplified, newItem: TrackSimplified): Boolean =
            oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(
                DataBindingUtil.inflate(inflater, R.layout.item_track, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    class Holder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrackSimplified) {
            binding.item = item
        }
    }
}