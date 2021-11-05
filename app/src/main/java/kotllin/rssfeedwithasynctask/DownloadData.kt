package kotllin.rssfeedwithasynctask

import android.os.AsyncTask
import android.util.Log
import kotllin.rssfeedwithasynctask.model.DownloadDataViewModel
import java.net.URL
import kotlin.properties.Delegates

class DownloadData(private val context: DownloadedCallBack) : AsyncTask<String, Void, String>(){
    private val TAG = "DownloadData"

    interface DownloadedCallBack{
        fun onDataAvailable(data:List<FeedEntry>)
    }
    init {
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // Log.d(TAG, "onPostExecute: parameter is $result")
        val parseApplications = ParseApplications()
        parseApplications.parse(result)
        context.onDataAvailable(parseApplications.applications)

    }

    override fun doInBackground(vararg url: String?): String {
//                Log.d(TAG, "doInBackground: starts with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
            Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
    }

    private fun downloadXML(urlPath: String?): String {
        return URL(urlPath).readText()
    }
}