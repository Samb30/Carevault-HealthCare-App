package com.example.carevault.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carevault.R
import com.example.carevault.news2

class NewwsAdapter(private val context: Context, private val listener: NewsClicked, private val articles: List<news2>)
    : RecyclerView.Adapter<ArticlesView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = ArticlesView(view)
        view.setOnClickListener{
            listener.onItemClicked(articles[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticlesView, position: Int) {
        val currentItem = articles[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        if(holder.author.text.toString().equals("") || holder.author.text.toString().equals(null)){
            holder.author.text="The Guardian"
        }
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
        holder.date.text = currentItem.publishedAt
        holder.share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, currentItem.url) // You can add the text you want to share here
            val chooser = Intent.createChooser(intent, "Share using....")
            context.startActivity(chooser)
        }
//        val s=holder.date.text
//        val p="| "
//        val res=p+s
//        holder.date.text=res
    }
}

interface NewsClicked {
    fun onItemClicked(articles: news2)
}

class ArticlesView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
    val date: TextView = itemView.findViewById(R.id.date)
    val share: ImageButton=itemView.findViewById(R.id.share)
}
