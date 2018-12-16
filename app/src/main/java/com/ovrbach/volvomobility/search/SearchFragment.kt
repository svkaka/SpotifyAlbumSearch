package com.ovrbach.volvomobility.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ovrbach.volvomobility.MINIMUM_QUERY_LENGTH
import com.ovrbach.volvomobility.MainViewModel
import com.ovrbach.volvomobility.R
import com.ovrbach.volvomobility.databinding.FragmentSearchBinding
import com.ovrbach.volvomobility.util.RequestStatus
import com.ovrbach.volvomobility.util.gone
import com.ovrbach.volvomobility.util.show
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fragment_search_edit_text.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.updateQuery(s.toString())
                    if (s?.length ?: 0 < MINIMUM_QUERY_LENGTH) {
                        view.fragment_search_edit_text.error = "Type at least 3 characters"
                    }
                }
            })
            setText("Hello")
        }
    }
}