package com.elmachos.stockviewer.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R

class MenuViewAdapter(private val itemListData: Array<ListData>) : RecyclerView.Adapter<MenuViewAdapter.ViewHolder>() {

    class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val imageView: ImageView = listItemView.findViewById(R.id.item_img_mnu_view)
        val textView: TextView = listItemView.findViewById(R.id.item_txt_mnu_view) as TextView
        val layout: RelativeLayout = listItemView.findViewById(R.id.mnu_items_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListData: ListData = itemListData[position]
        holder.textView.text = itemListData[position].description
        holder.imageView.setImageResource(itemListData[position].imgId)
        holder.layout.setOnClickListener { view ->
            Toast.makeText(
                view.context,
                "click on item: " + myListData.description,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount() = itemListData.size

}