package com.elmachos.stockviewer.activity.stockview.table

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.menu.MenuViewAdapter
import com.elmachos.stockviewer.domain.Currency
import com.elmachos.stockviewer.domain.ShareInformation
import com.elmachos.stockviewer.domain.ShareInformationType
import com.elmachos.stockviewer.domain.StockExchange
import kotlinx.android.synthetic.main.fragment_stock_shares_chart_view.*
import java.time.LocalDate
import java.util.*

private const val STOCK = "stock"


class StockSharesTableViewFragment : Fragment() {

    private var stock: StockExchange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stock = it.getSerializable(STOCK) as StockExchange
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock_shares_table_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupTableView()
    }

    companion object {
        @JvmStatic
        fun newInstance(stock: StockExchange) =
            StockSharesTableViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STOCK, stock)
                }
            }
    }

    private fun setupTitle() {
        stock_title.text = stock?.stockName
    }

    private fun setupTableView(){
        val recyclerView: RecyclerView? = activity?.findViewById(R.id.table_view_rec_view)
        val adapter = TableViewAdapter(dummyData())
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter
    }

    private fun dummyData(): List<ShareViewData> {
        return listOf(
            ShareViewData("share1", 15.0, 21.77,Currency.PLN, Date()),
            ShareViewData("share2", 15.0, 21.77,Currency.PLN, Date()),
            ShareViewData("share3", 15.0, 21.77,Currency.PLN, Date())
            )
    }
}
