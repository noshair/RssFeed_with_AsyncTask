package kotllin.rssfeedwithasynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    val TAG = "Noshair"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        val execute = DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
    }

    private inner class DownloadData : AsyncTask<String, Void, String>() {
        val TAG = "DownloadData"

        override fun doInBackground(vararg params: String?): String {
            Log.d(TAG, "doInBackground")
            val rrsFeed = DownloadXML(params[0])
            if(rrsFeed.isNotEmpty()){
                Log.d(TAG,"doing in background")
            }
return rrsFeed
        }

        private fun DownloadXML(urlPath: String?): String {
            val xmlResult = StringBuffer()
            val url = URL(urlPath)
            try {
                var httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val response = httpURLConnection.responseCode
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)
                val reader = BufferedReader(inputStreamReader)
                  val   inputBuffer=   CharArray(500)
                var charRead =0

                if(charRead>=0){
                    charRead=reader.read(inputBuffer)
                    if (charRead>0){
                        xmlResult.append(String(inputBuffer,0,charRead))
                    }
                }
                reader.close()
                Log.d(TAG,"Lenght${xmlResult.length}")
                return xmlResult.toString()

            }catch (e: MalformedURLException){
                Log.d(TAG, "Invalid url{${e.message}}")

            } catch (e: IOException) {
                Log.d(TAG, "IO  Exception{${e.message}}")

            }catch (e:SecurityException){
                Log.d(TAG, "SecurityException{${e.message}}")

            }
            catch (e:Exception){
                Log.d(TAG, "Exception{${e.message}}")

            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(TAG, "onPostExecute$result")

        }
    }
}