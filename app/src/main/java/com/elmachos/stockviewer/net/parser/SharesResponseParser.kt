package com.elmachos.stockviewer.net.parser

import android.os.AsyncTask
import android.util.Log
import com.elmachos.stockviewer.domain.ShareInformationRecord

data class ParsingParams(val response: String?, val parsingCallback: (res: ShareInformationRecord) -> Any = {})

abstract class SharesResponseParser: AsyncTask<ParsingParams, Unit, ShareInformationRecord>() {

    private val TAG = "PARSER"
    private val responseValidityPredicate = { res: String -> res.contains("<body>")}
    private lateinit var parsingParams: ParsingParams

    abstract fun doParse(response: String?): ShareInformationRecord

    protected fun preParse(response: String?) {
        if(response == null) throw Exception("Cannot parse response: $response")
        if(!responseValidityPredicate.invoke(response)) throw Exception("Cannot parse invalid response")
    }

    protected fun parse(response: String?): ShareInformationRecord{
        preParse(response)
        return doParse(response)
    }

    override fun doInBackground(vararg params: ParsingParams?): ShareInformationRecord {
        Log.d(TAG, "Starting parsing task")
        parsingParams=params.first()!!
        return parse(parsingParams.response)
    }

    override fun onPostExecute(result: ShareInformationRecord) {
        super.onPostExecute(result)
        Log.d(TAG, "Parsing has been completed, invoking callback")
        parsingParams.parsingCallback.invoke(result)
    }

}