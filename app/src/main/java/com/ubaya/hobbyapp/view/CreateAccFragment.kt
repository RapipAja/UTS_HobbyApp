package com.ubaya.hobbyapp.view

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
import com.ubaya.hobbyapp.databinding.FragmentCreateAccBinding
import com.ubaya.hobbyapp.viewmodel.UserViewModel


class CreateAccFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreate.setOnClickListener {
            var username = binding.txtNewUser.text.toString()
            var firstname = binding.txtFName.text.toString()
            var lastname = binding.txtLName.text.toString()
            var password = binding.txtNewPass.text.toString()
            var rePassword = binding.txtRePass.text.toString()
            var picture = binding.txtPict.text.toString()

            if (password == rePassword){
                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                viewModel.createUser(username, firstname, lastname, password, picture)

                viewModel.createLD.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        val action = CreateAccFragmentDirections.actionLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else {
                        Toast.makeText(activity, "Register Filed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else {
                Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

}