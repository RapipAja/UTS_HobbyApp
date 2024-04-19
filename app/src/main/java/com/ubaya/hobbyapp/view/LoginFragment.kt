package com.ubaya.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.hobbyapp.R
import com.ubaya.hobbyapp.databinding.FragmentLoginBinding
import com.ubaya.hobbyapp.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            viewModel.loginCheck(binding.txtUser.text.toString(), binding.txtPass.text.toString())

            viewModel.userLD.observe(viewLifecycleOwner, Observer {
                var login = it

                if (login != null) {
                    val action = LoginFragmentDirections.actionHomeFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                else {
//                    Toast.makeText(activity, "No user found with that username or password", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionCreateAccFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

}