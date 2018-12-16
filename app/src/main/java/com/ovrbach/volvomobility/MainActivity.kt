package com.ovrbach.volvomobility

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ovrbach.volvomobility.detail.ALBUM_ID
import com.ovrbach.volvomobility.detail.DETAIL_FRAGMENT_TAG
import com.ovrbach.volvomobility.detail.DetailFragment

class MainActivity : AppCompatActivity(), NavigationListener {
    private lateinit var viewModel: MainViewModel


    override fun showAlbumDetails(id: String, sharedElement: View) {
        val bundle = Bundle().apply {
            putString(ALBUM_ID, id)
        }
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content,
                        DetailFragment().apply {
                            arguments = bundle
                        })
                .addSharedElement(sharedElement, "album_cover")
                .addToBackStack(null)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

}
