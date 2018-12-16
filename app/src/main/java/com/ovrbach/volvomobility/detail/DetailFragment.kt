package com.ovrbach.volvomobility.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ovrbach.volvomobility.R
import com.ovrbach.volvomobility.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.view.*
import android.media.MediaPlayer
import com.ovrbach.volvomobility.util.gone


const val ALBUM_ID = "albumId"
const val DETAIL_FRAGMENT_TAG = "DetailFragment"

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var tracksAdapter: DetailsTracksAdapter

    private val mediaPlayer: MediaPlayerProvider by lazy {
        MediaPlayerProvider()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        lifecycle.addObserver(mediaPlayer)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumId = arguments?.getString(ALBUM_ID)
        viewModel.getAlbumDetails(albumId)

        view.fragment_detail_tracks_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            tracksAdapter = DetailsTracksAdapter()
            adapter = tracksAdapter
        }

        viewModel.result.observe(this, Observer { album ->
            if (album != null) {
                tracksAdapter.submitList(album.getTracksList())
                binding.album = album
                binding.executePendingBindings()

                album.getTracksList().firstOrNull {
                    it.preview_url != null
                }?.preview_url?.let {

                    mediaPlayer.addTrackToQueue(it)

                    view.album_detail_play_pause_button.apply {
                        setOnClickListener {
                            val isPlaying = mediaPlayer.toggle()
                            isActivated = isPlaying
                        }
                    }

                } ?: view.album_detail_play_pause_button.gone()
            }

        })
    }

}