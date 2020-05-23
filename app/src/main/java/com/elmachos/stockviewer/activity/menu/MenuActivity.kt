package com.elmachos.stockviewer.activity.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.domain.StockExchange

const val SELECTED_STOCK = "com.elmachos.activites.menu.STOCK"

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setupMenuRecyclerView()

    }

    private fun setupMenuRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rec_view)
        val adapter = MenuViewAdapter(getItems())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun getItems(): Array<ListData> {
        return StockExchange.values()
            .map { ListData(it.name, R.drawable.ic_money, it) }
            .toTypedArray()
    }
}
