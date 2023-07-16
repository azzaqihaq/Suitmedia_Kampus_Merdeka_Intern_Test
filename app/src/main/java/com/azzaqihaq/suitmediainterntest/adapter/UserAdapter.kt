package com.azzaqihaq.suitmediainterntest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azzaqihaq.suitmediainterntest.R
import com.azzaqihaq.suitmediainterntest.model.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val list: ArrayList<Data>, private val onUserClickListener: OnUserClickListener): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onUserClickListener.onUserItemClicked(position)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class UserViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        fun bind(userResponse: Data) {
            with(itemView) {
                val firstName = userResponse?.first_name
                val lastName  = userResponse?.last_name
                val email     = userResponse?.email
                val avatar    = userResponse?.avatar

                name.text = firstName + " " + lastName
                email_user.text = email
                Picasso.with(context)
                    .load(avatar)
                    .resize(140, 140)
                    .into(avatar_user)
            }
        }
    }


    fun addList(items: ArrayList<Data>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

}