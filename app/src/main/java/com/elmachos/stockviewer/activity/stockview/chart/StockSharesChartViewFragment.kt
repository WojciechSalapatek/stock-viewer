package com.elmachos.stockviewer.activity.stockview.chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.domain.StockExchange
import kotlinx.android.synthetic.main.fragment_stock_shares_chart_view.*

private const val STOCK = "stock"

class StockSharesChartViewFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_stock_shares_chart_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
    }

    companion object {
        @JvmStatic
        fun newInstance(stock: StockExchange) =
            StockSharesChartViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STOCK, stock)
                }
            }
    }

    private fun setupTitle() {
        stock_title.text = stock?.stockName
    }
}
