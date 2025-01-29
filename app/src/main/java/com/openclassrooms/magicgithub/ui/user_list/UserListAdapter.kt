package com.openclassrooms.magicgithub.ui.user_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.magicgithub.databinding.ItemListUserBinding
import com.openclassrooms.magicgithub.model.User
import java.util.*

class UserListAdapter(private val listener: Listener) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    fun updateList(newUsers: List<User>) {
        val oldList = users.toList()
        users.clear()
        users.addAll(newUsers)
        // Utilisation d'une notification plus spécifique
        if (oldList.size == newUsers.size) {
            notifyItemRangeChanged(0, newUsers.size)
        } else {
            notifyDataSetChanged()
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(users, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(users, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun toggleUserActive(position: Int) {
        users[position].isActive = !users[position].isActive
        notifyItemChanged(position)
    }

    inner class ViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            // Définir la couleur de fond en fonction de l'état actif
            binding.root.setBackgroundColor(if (user.isActive) Color.WHITE else Color.RED)

            // Mettre à jour le nom d'utilisateur - correction de l'ID
            binding.itemListUserUsername.text = user.login

            // Charger l'avatar
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.itemListUserAvatar)

            // Configuration du bouton de suppression
            binding.itemListUserDeleteButton.setOnClickListener {
                listener.onClickDelete(user)
            }
        }
    }

    interface Listener {
        fun onClickDelete(user: User)
    }
}