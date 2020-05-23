package com.elmachos.stockviewer.activity.stockview.table

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R

class TableViewAdapter(private val itemListData: List<ShareViewData>) :
    RecyclerView.Adapter<TableViewAdapter.ViewHolder>() {

    class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val shareNameView: TextView = listItemView.findViewById(R.id.table_view_item_share_name) as TextView
        val shareOpenPriceView: TextView = listItemView.findViewById(R.id.table_view_item_open_price) as TextView
        val shareClosePriceView: TextView = listItemView.findViewById(R.id.table_view_item_close_price) as TextView
        val shareCurrency: TextView = listItemView.findViewById(R.id.table_view_item_currency) as TextView
        val shareDate: TextView = listItemView.findViewById(R.id.table_view_item_date) as TextView
        val layout: LinearLayout = listItemView.findViewById(R.id.table_view_item_layout_port)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.tab_view_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0) {
            holder.shareNameView.setBackgroundResource(R.drawable.border_header)
            holder.shareOpenPriceView.setBackgroundResource(R.drawable.border_header)
            holder.shareClosePriceView.setBackgroundResource(R.drawable.border_header)
            holder.shareCurrency.setBackgroundResource(R.drawable.border_header)
            holder.shareDate.setBackgroundResource(R.drawable.border_header)
            holder.shareNameView.setTextColor(Color.WHITE)
            holder.shareOpenPriceView.setTextColor(Color.WHITE)
            holder.shareClosePriceView.setTextColor(Color.WHITE)
            holder.shareCurrency.setTextColor(Color.WHITE)
            holder.shareDate.setTextColor(Color.WHITE)
            return
        }
        val shareData: ShareViewData = itemListData[position-1]
        holder.shareNameView.text = itemListData[position-1].shareName
        holder.shareOpenPriceView.text = itemListData[position-1].openVal?.toString()
        holder.shareClosePriceView.text = itemListData[position-1].closeVal?.toString()
        holder.shareCurrency.text = itemListData[position-1].currency.label
        holder.shareDate.text = itemListData[position-1].date.toString()
    }

    override fun getItemCount() = itemListData.size + 1

}