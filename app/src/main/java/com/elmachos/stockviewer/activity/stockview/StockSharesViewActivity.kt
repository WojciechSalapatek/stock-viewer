package com.elmachos.stockviewer.activity.stockview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle    
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.menu.SELECTED_STOCK
import com.elmachos.stockviewer.activity.stockview.chart.StockSharesChartViewFragment
import com.elmachos.stockviewer.activity.stockview.table.StockSharesTableViewFragment
import com.elmachos.stockviewer.domain.StockExchange
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

}
