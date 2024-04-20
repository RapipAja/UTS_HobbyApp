package com.ubaya.hobbyapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.hobbyapp.R
import com.ubaya.hobbyapp.databinding.FragmentLoginBinding
import com.ubaya.hobbyapp.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

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

            var username = binding.txtUser.text.toString()
            var pass = binding.txtPass.text.toString()

            if (username.isNotEmpty() && pass.isNotEmpty()) {
                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                viewModel.loginCheck(
                    binding.txtUser.text.toString(),
                    binding.txtPass.text.toString()
                )

                viewModel.loginLD.observe(viewLifecycleOwner, Observer {success->
                    if (success) {
                        val account = requireActivity().getSharedPreferences("saveUser", Context.MODE_PRIVATE)
                        val loginVal = account.edit()

                        viewModel.userLD.value?.let { login->
                            loginVal.putString("id", login.id)
                            loginVal.putString("first_name", login.firstname)
                            loginVal.putString("last_name", login.lastname)
                            loginVal.putString("pass", login.password)
                            loginVal.putString("picture", login.picture)
                            loginVal.apply()
                        }
                        mainActivity.navShow()
                        val action = LoginFragmentDirections.actionHomeFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else{
                Toast.makeText(requireContext(), "Please enter corect username anda password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionCreateAccFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

}