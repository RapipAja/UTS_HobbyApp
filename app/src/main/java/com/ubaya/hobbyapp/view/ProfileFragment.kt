package com.ubaya.hobbyapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.ubaya.hobbyapp.R
import com.ubaya.hobbyapp.databinding.FragmentHomeBinding
import com.ubaya.hobbyapp.databinding.FragmentProfileBinding
import com.ubaya.hobbyapp.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userLogin = requireActivity().getSharedPreferences("saveUser", Context.MODE_PRIVATE)
        val id = userLogin.getString("id", null)
        val oldFirstName = userLogin.getString("first_name", null)
        val oldLastName = userLogin.getString("last_name", null)
        val oldPassword = userLogin.getString("pass", null)
        val picture = userLogin.getString("picture", null)

        Picasso.get().load(picture).into(binding.imgProfile)
        binding.txtNFirstname.setText(oldFirstName)
        binding.txtNLastname.setText(oldLastName)

        binding.btnChange.setOnClickListener {
            if (oldPassword == binding.txtOldPass.text.toString()) {
                viewModel.changeUser(id.toString(), binding.txtNFirstname.text.toString(), binding.txtNLastname.text.toString(),
                    binding.txtNPass.text.toString())

                viewModel.updateLD.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        binding.txtOldPass.setText("")
                        binding.txtNPass.setText("")
                    }
                })
            }
        }

        binding.btnLogOut.setOnClickListener {
            val pref = requireActivity().getSharedPreferences("saveUser", Context.MODE_PRIVATE)
            val prefEdit = pref.edit()
            prefEdit.remove("id")
            prefEdit.remove("first_name")
            prefEdit.remove("last_name")
            prefEdit.remove("pass")
            prefEdit.remove("picture")

            mainActivity.navhide()
            val action = ProfileFragmentDirections.actionLogoutLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

}