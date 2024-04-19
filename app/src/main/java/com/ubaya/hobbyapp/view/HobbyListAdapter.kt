package com.ubaya.hobbyapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.hobbyapp.databinding.ListItemHobbyBinding
import com.ubaya.hobbyapp.model.Hobby
import java.lang.Exception

class HobbyListAdapter(var listHobby:ArrayList<Hobby>):RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(var binding:ListItemHobbyBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = ListItemHobbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHobby.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        with(holder.binding) {
            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener { picasso, uri, exception -> exception.printStackTrace() }
            picasso.build().load(listHobby[position].image_url).into(imgHobby, object:Callback {
                override fun onSuccess() {
                    loadingImg.visibility = View.INVISIBLE
                    imgHobby.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("img_error", e.toString())
                }
            })

            txtTitleList.text = listHobby[position].title
            txtAuth.text = "@" + listHobby[position].author
            txtDesc.text = listHobby[position].description
            btnRead.setOnClickListener {
                val action = HomeFragmentDirections.actionDetailFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateHobby(newListHobby:ArrayList<Hobby>){
        listHobby.clear()
        listHobby.addAll(newListHobby)
        notifyDataSetChanged()
    }
}