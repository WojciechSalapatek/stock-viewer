package com.elmachos.stockviewer.activity.stockview.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.domain.ShareInformationRecord
import com.elmachos.stockviewer.domain.StockExchange
import com.elmachos.stockviewer.net.ServiceLocator
import kotlinx.android.synthetic.main.fragment_stock_shares_chart_view.*
import org.apache.commons.lang3.time.DateUtils
import java.util.*

private const val STOCK = "stock"


class StockSharesTableViewFragment : Fragment() {

    private var stock: StockExchange? = null
    private val adapterDataSet: MutableList<ShareViewData> = emptyList<ShareViewData>().toMutableList()
    private lateinit var adapter: TableViewAdapter

    companion object {
        @JvmStatic
        fun newInstance(stock: StockExchange) =
            StockSharesTableViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STOCK, stock)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stock = it.getSerializable(STOCK) as StockExchange
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_shares_table_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupTableView()
    }

    private fun setupTitle() {
        stock_title.text = stock?.stockName
    }

    private fun setupTableView(){
        val recyclerView: RecyclerView? = activity?.findViewById(R.id.table_view_rec_view)
        adapter = TableViewAdapter(adapterDataSet)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter

        getData()
    }

    private fun getData() {
        ServiceLocator.getSharesDataProvider().fetchAndParseForStockAndDate(stock,  getLastNonWeekendDateOrYesterday(), resultCallback = {onDataUpdated(it)})
    }

    private fun getLastNonWeekendDateOrYesterday(): Date {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
        var date = cal.time
        date = DateUtils.addDays(date, -1)
        when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> date = DateUtils.addDays(date, -2)
            Calendar.SATURDAY -> date = DateUtils.addDays(date, -1)
        }
        return date
    }

    private fun onDataUpdated(data: ShareInformationRecord) {
        adapterDataSet.addAll(data.toView())
        adapter.notifyDataSetChanged()
    }
}
