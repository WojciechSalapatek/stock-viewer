package com.elmachos.stockviewer.activity.savedstocks

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmachos.stockviewer.R
import com.elmachos.stockviewer.activity.stockview.table.ShareViewData
import com.elmachos.stockviewer.activity.stockview.table.TableViewAdapter
import com.elmachos.stockviewer.domain.Currency
import com.elmachos.stockviewer.storage.StockDatabase
import com.elmachos.stockviewer.storage.entities.StockData
import java.util.*

class SavedSharesActivity : AppCompatActivity() {
    private lateinit var adapter: TableViewAdapter
    private val adapterDataSet: MutableList<ShareViewData> = emptyList<ShareViewData>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_stock_shares_view)

        //initially show all records stored in data base
        updateDataSet(StockDatabase.getDatabase(context = this).stockDataDao().getAll())
        setupTableView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.db_menu, menu)
        return true
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.search_by_share_name_dialog)
        val body = dialog.findViewById(R.id.dialog_title) as TextView
        body.text = title
        val searchButton = dialog.findViewById(R.id.button_search) as Button
        val cancelButton = dialog.findViewById(R.id.button_cancel) as TextView
        val editText = dialog.findViewById(R.id.edit_text) as EditText
        if (title.equals("Search by date")) {
            editText.setHint("yyyy/mm/dd")
        }
        searchButton.setOnClickListener {
            if (title.equals("Search by share name")) {
                updateDataSet(StockDatabase.getDatabase(context = this).stockDataDao().getByStockName(editText.text.toString().toUpperCase()))
                setupTableView()
            }
            else if (title.equals("Search by date")) {
                updateDataSet(StockDatabase.getDatabase(context = this).stockDataDao().getByDate(editText.text.toString()))
                setupTableView()
            }
            dialog.dismiss()
        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.search_by_share_name -> {
                showDialog("Search by share name")
                true
            }
            R.id.search_by_date -> {
                showDialog("Search by date")
                true
            }
            R.id.delete_all_data -> {
                StockDatabase.getDatabase(this).stockDataDao().deleteAll()
                adapterDataSet.clear()
                setupTableView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateDataSet(shareDbList: List<StockData>) {
        adapterDataSet.clear()
        for (share in shareDbList) {
            share.shareName?.let { ShareViewData(it, share.openVal, share.closeVal, Currency.PLN, Date(share.date)) }
                ?.let { adapterDataSet.add(it) }
        }
    }

    private fun setupTableView() {
        val recyclerView: RecyclerView? = findViewById(R.id.table_view_rec_view)
        recyclerView?.setHasFixedSize(true)
        adapter = TableViewAdapter(adapterDataSet)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

}
