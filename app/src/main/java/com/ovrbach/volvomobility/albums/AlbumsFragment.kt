package com.ovrbach.volvomobility.albums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ovrbach.common.entities.AlbumSimplified
import com.ovrbach.volvomobility.MainViewModel
import com.ovrbach.volvomobility.NavigationListener
import com.ovrbach.volvomobility.OnItemClickListener
import com.ovrbach.volvomobility.R
import com.ovrbach.volvomobility.databinding.FragmentAlbumsBinding
import kotlinx.android.synthetic.main.fragment_albums.view.*
import java.lang.IllegalStateException

class AlbumsFragment : Fragment(), OnItemClickListener<AlbumSimplified> {

    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var navigationListener: NavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationListener) {
            navigationListener = context
        } else {
            IllegalStateException("Activity must implement NavigationListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fragment_albums_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            albumsAdapter = AlbumsAdapter(navigationListener)
            adapter = albumsAdapter
        }


        viewModel.result.observe(
                this, Observer { albums ->
            albumsAdapter.submitList(albums)
        })

    }

    override fun onItemClicked(item: AlbumSimplified) {
    }
}