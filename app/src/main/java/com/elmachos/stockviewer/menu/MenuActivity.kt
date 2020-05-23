package com.elmachos.stockviewer.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.domain.StockExchangeProvider

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val recyclerView: RecyclerView = findViewById(R.id.rec_view)
        val adapter = MenuViewAdapter(getItems())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun getItems(): Array<ListData> {
        return StockExchangeProvider.getDefinedStockExchanges()
            .map { ListData(it.name, R.drawable.ic_money) }
            .toTypedArray()
    }
}
