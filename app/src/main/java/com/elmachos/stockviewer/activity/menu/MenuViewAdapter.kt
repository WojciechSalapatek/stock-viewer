package com.elmachos.stockviewer.activity.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.stockview.StockSharesViewActivity

class MenuViewAdapter(private val itemListData: Array<ListData>) :
    RecyclerView.Adapter<MenuViewAdapter.ViewHolder>() {

    val TAG = "MENU"

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
        val stock: ListData = itemListData[position]
        holder.textView.text = itemListData[position].description
        holder.imageView.setImageResource(itemListData[position].imgId)
        holder.layout.setOnClickListener { view ->
            Log.d(TAG, "Selected stock: ${stock.stock.stockName}")
            val intent = Intent(view.context, StockSharesViewActivity::class.java).apply {
                putExtra(SELECTED_STOCK, stock.stock)
            }
            startActivity(view.context, intent, Bundle())
        }
    }

    override fun getItemCount() = itemListData.size

}