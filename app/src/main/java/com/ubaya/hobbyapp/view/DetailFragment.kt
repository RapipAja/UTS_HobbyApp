package com.ubaya.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.ubaya.hobbyapp.R
import com.ubaya.hobbyapp.databinding.FragmentDetailBinding
import com.ubaya.hobbyapp.viewmodel.HobbyViewModel


class DetailFragment : Fragment() {
    private lateinit var  binding: FragmentDetailBinding
    private lateinit var viewModel: HobbyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
            viewModel.detailHobby(DetailFragmentArgs.fromBundle(requireArguments()).id)

            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.hobbyDetailLD.observe(viewLifecycleOwner, Observer {
            if (it == null) {
            }
            else {
                binding.txtTitleD.setText(it.title)
                binding.txtAuthD.setText("@"+it.author)
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener { picasso, uri, exception ->  exception.printStackTrace()}
                picasso.build().load(it.image_url).into(binding.imgDetail)

                var contents = it.content?.size ?:0
                var index = 0
                if (contents > 0) {
                    binding.txtContent.setText(it.content?.get(index))
                    binding.btnPrev.isEnabled = false

                    binding.btnNext.setOnClickListener { view ->
                        index += 1
                        if (index < contents) {
                            binding.txtContent.setText(it.content?.get(index))
                            binding.btnPrev.isEnabled = true
                        } else {
                            Toast.makeText(requireContext(), "No more content", Toast.LENGTH_SHORT).show()
                            index -= 1
                        }
                        binding.btnNext.isEnabled = index < contents - 1
                    }

                    binding.btnPrev.setOnClickListener { view ->
                        index -= 1
                        if (index >= 0) {
                            binding.txtContent.setText(it.content?.get(index))
                            binding.btnNext.isEnabled = true
                        } else {
                            index += 1
                        }
                        binding.btnPrev.isEnabled = index > 0
                    }

                }
                else {
                    binding.txtContent.setText("There is no content")
                }
            }
        })
    }

}