package com.elmachos.stockviewer.activity.stockview.chart

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.domain.ShareInformationRecord
import com.elmachos.stockviewer.domain.StockExchange
import com.elmachos.stockviewer.net.ServiceLocator
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_stock_shares_chart_view.*
import org.apache.commons.lang3.time.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val STOCK = "stock"
private const val WHOLE_DATA = "wholeData"
private const val EXiSTING_SELECTION = "existingSelection"

class StockSharesChartViewFragment : Fragment() {

    private var stock: StockExchange? = null
    private var wholeData: MutableList<ShareInformationRecord> = mutableListOf()
    private var existingSelection: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stock = it.getSerializable(STOCK) as StockExchange
            wholeData =
                (savedInstanceState?.getSerializable(WHOLE_DATA) as Array<ShareInformationRecord>?)?.toList()?.toMutableList()
                    ?: mutableListOf()
            existingSelection = savedInstanceState?.getString(EXiSTING_SELECTION)
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
        if (wholeData.isEmpty()) getDataForFiveDaysPast()
        else {
            setupDropdown()
            setupBarChart(existingSelection!!)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXiSTING_SELECTION, existingSelection)
        val data: Array<ShareInformationRecord> = wholeData.toTypedArray()
        outState.putSerializable(WHOLE_DATA, data)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        wholeData =
            (savedInstanceState?.getSerializable(WHOLE_DATA) as Array<ShareInformationRecord>?)?.toList()?.toMutableList()
                ?: mutableListOf()
        existingSelection = savedInstanceState?.getString(EXiSTING_SELECTION)
    }

    private fun setupTitle() {
        stock_title?.text = stock?.stockName
    }

    private fun setupBarChart(shareName: String) {
        val orientation = this.resources.configuration.orientation;
        val nRecords = if (orientation == Configuration.ORIENTATION_PORTRAIT) 5 else 10
        val entries: MutableList<BarEntry> = wholeData
            .subList(0, nRecords - 1)
            .map { it.toView() }
            .mapIndexed { index, list ->
                BarEntry(
                    index.toFloat(),
                    list.find { it.shareName == shareName }?.closeVal?.toFloat() ?: 0f
                )
            }
            .toMutableList()

        val min: Float = entries.filter { it.y != 0.0f }.minBy { it.y }!!.y
        val max: Float = entries.maxBy { it.y }!!.y

        val xAxisLabels = entries.mapIndexed { index, _ ->
            SimpleDateFormat(
                "dd-MM",
                Locale.UK
            ).format(DateUtils.addDays(Date(), -index))
        }

        val set = BarDataSet(entries, shareName)
        chart_view_bar_chart.data = BarData(set)
        chart_view_bar_chart.setFitBars(true)
        val description = Description()
        description.text = ""
        chart_view_bar_chart.description = description
        chart_view_bar_chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        chart_view_bar_chart.axisLeft.axisMinimum = min - (max-min)/3
        chart_view_bar_chart.axisLeft.axisMaximum = max + (max-min)/3
        chart_view_bar_chart.invalidate()
    }

    private fun clearBarChart() {
        val entries: MutableList<BarEntry> = ArrayList()

        val set = BarDataSet(entries, "No share selected")
        chart_view_bar_chart.data = BarData(set)
        chart_view_bar_chart.setFitBars(true)
        chart_view_bar_chart.invalidate()
    }

    private fun setupDropdown() {
        if (stock_names_dropdown_menu == null) return
        val data = wholeData.map { it.toView() }.maxBy { it.size }!!.map { it.shareName }
        stock_names_dropdown_menu?.adapter = ArrayAdapter<String>(
            this.context!!, R.layout.support_simple_spinner_dropdown_item,
            data
        )
        stock_names_dropdown_menu?.onItemSelectedListener = MenuItemSelectionListener()
        if (existingSelection != null) stock_names_dropdown_menu?.setSelection(
            data.indexOf(
                existingSelection!!
            )
        )
    }

    private fun getDataForFiveDaysPast() {
        wholeData.removeAll { true }
        val dataProvider = ServiceLocator.getSharesDataProvider()
        val today = Date()
        for (i in 0..10) dataProvider.fetchAndParseForStockAndDate(
            stock,
            DateUtils.addDays(today, -i),
            resultCallback = { wholeDataAppender(it, 10) })
    }

    private fun wholeDataAppender(newRecord: ShareInformationRecord, expectedRecords: Int) {
        wholeData.add(newRecord)
        if (wholeData.size == expectedRecords) {
            setupDropdown()
        }
    }

    inner class MenuItemSelectionListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            clearBarChart()
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            existingSelection =
                wholeData.map { it.toView() }.maxBy { it.size }!![position].shareName
            setupBarChart(existingSelection!!)
        }

    }
}
