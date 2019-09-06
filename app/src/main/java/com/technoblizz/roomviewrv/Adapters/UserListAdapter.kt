package com.technoblizz.roomviewrv.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technoblizz.roomviewrv.Database.UserEntity
import com.technoblizz.roomviewrv.R

class UserListAdapter internal constructor(context: Context): RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<UserEntity>() // Cached copy of users

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_username: TextView = itemView.findViewById(R.id.txt_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.txt_username.text = current.username
    }

    internal fun setUser(users: List<UserEntity>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }

}