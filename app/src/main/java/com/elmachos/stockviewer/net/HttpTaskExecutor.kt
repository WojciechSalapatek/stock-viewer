package com.elmachos.stockviewer.net

import android.os.AsyncTask
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

data class RequestParams(val url: String, val responseCallback: (response: String?) -> Unit = {})

class HttpTaskExecutor : AsyncTask<RequestParams, Unit, String>() {

    private val TAG = "HTTP_EXECUTOR"
    private lateinit var requestParams: RequestParams

    override fun doInBackground(vararg params: RequestParams?): String {
        Log.d(TAG, "Sending 'GET' request to URL : ${params.first()}")
        requestParams = params.first()!!
        return makeGet(requestParams.url)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d(TAG, "Response: ${result?.contains("<body>")}")
        requestParams.responseCallback.invoke(result)
    }

    private fun makeGet(aUrl: String): String {
        val url = URL(aUrl)

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            Log.d(TAG, "Sent 'GET' request to URL : $url; Response Code : $responseCode")
            return inputStream.readBytes().toString(Charsets.UTF_8)
        }
    }
}