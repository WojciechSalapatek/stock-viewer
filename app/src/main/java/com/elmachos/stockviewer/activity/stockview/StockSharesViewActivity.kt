package com.elmachos.stockviewer.activity.stockview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.menu.SELECTED_STOCK
import com.elmachos.stockviewer.activity.savedstocks.SavedSharesActivity
import com.elmachos.stockviewer.activity.stockview.chart.StockSharesChartViewFragment
import com.elmachos.stockviewer.activity.stockview.table.StockSharesTableViewFragment
import com.elmachos.stockviewer.domain.StockExchange
import kotlinx.android.synthetic.main.activity_stock_shares_view.*

class StockSharesViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_shares_view)

        val stock = intent.getSerializableExtra(SELECTED_STOCK) as StockExchange

        if(savedInstanceState == null) {
            val tableView = StockSharesTableViewFragment.newInstance(stock)
            val chartView = StockSharesChartViewFragment.newInstance(stock)
            setupFragments(tableView, chartView)
        }
    }

    private fun setupFragments(
        tableView: StockSharesTableViewFragment,
        chartView: StockSharesChartViewFragment
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.stock_shares_view, tableView)
            commit()
        }

        setupIfNotNull(table_view_button) { btn ->
            btn.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.stock_shares_view, tableView)
                    commit()
                }
            }
        }

        setupIfNotNull(chart_view_button) { btn ->
            btn.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.stock_shares_view, chartView)
                    commit()
                }
            }
        }
    }

    private fun setupIfNotNull(btn: Button?, setup: (b: Button) -> Unit) {
        if (btn != null) {
            setup(btn)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.show_db -> {
                showDbRecords()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDbRecords() {
        val intent = Intent(this, SavedSharesActivity::class.java)
        startActivity(intent)
    }
}
