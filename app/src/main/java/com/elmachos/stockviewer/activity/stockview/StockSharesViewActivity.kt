package com.elmachos.stockviewer.activity.stockview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.menu.SELECTED_STOCK
import com.elmachos.stockviewer.activity.savedstocks.SavedSharesActivity
import com.elmachos.stockviewer.activity.stockview.chart.StockSharesChartViewFragment
import com.elmachos.stockviewer.activity.stockview.table.StockSharesTableViewFragment
import com.elmachos.stockviewer.domain.StockExchange
import com.elmachos.stockviewer.storage.StockDatabase
import kotlinx.android.synthetic.main.activity_stock_shares_view.*

class StockSharesViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_shares_view)

        val stock = intent.getSerializableExtra(SELECTED_STOCK) as StockExchange

        val tableView = StockSharesTableViewFragment.newInstance(stock)
        val chartView = StockSharesChartViewFragment.newInstance(stock)


        setupFragments(tableView, chartView)
    }

    private fun setupFragments(tableView: StockSharesTableViewFragment, chartView: StockSharesChartViewFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.stock_shares_view, tableView)
            commit()
        }

        table_view_button.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.stock_shares_view, tableView)
                commit()
            }
        }

        chart_view_button.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.stock_shares_view, chartView)
                commit()
            }
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
            R.id.save_to_db -> {
                saveToDb()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDbRecords() {
        val intent = Intent(this, SavedSharesActivity::class.java)
        startActivity(intent)
    }

    private fun saveToDb() {
        val stock = intent.getSerializableExtra(SELECTED_STOCK) as StockExchange
        println("gowno")
        //StockDatabase.getDatabase(this).stockDataDao(stock)
        //save to database
    }
}
