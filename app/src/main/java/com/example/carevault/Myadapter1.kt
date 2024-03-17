package com.example.carevault

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Myadapter1(private val userlist: ArrayList<User>) :
    RecyclerView.Adapter<Myadapter1.Myholder>() {
    class Myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.tvname)
        val Date: TextView = itemView.findViewById(R.id.tvname2)
        val Image: ImageView = itemView.findViewById(R.id.imshow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Myholder(itemView)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.Date.text = userlist[position].date
        holder.Name.text = userlist[position].name
        Picasso.get()
            .load(userlist[position].imageUrl)
            .into(holder.Image)
    }
}