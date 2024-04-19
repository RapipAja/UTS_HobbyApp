package com.ubaya.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ubaya.hobbyapp.R
import com.ubaya.hobbyapp.databinding.FragmentHomeBinding
import com.ubaya.hobbyapp.viewmodel.HobbyViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private  lateinit var  viewModel: HobbyViewModel
    private val listAdapter = HobbyListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = listAdapter

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)


        swipe.setOnRefreshListener {
            viewModel.refresh()
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE
            swipe.isRefreshing = false
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            listAdapter.updateHobby(it)
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.loading.visibility = View.INVISIBLE
            }
            else {
                binding.recView.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE
            }
        })

        viewModel.hobbyLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError?.visibility = View.VISIBLE
            }
            else {
                binding.txtError?.visibility = View.GONE
            }
        })
    }

}