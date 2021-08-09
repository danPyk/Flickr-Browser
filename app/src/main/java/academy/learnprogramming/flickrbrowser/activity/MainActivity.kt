package academy.learnprogramming.flickrbrowser.activity

import academy.learnprogramming.flickrbrowser.R
import academy.learnprogramming.flickrbrowser.RecyclerItemClickListener
import academy.learnprogramming.flickrbrowser.data.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

//todo change preferences to https://developer.android.com/reference/androidx/preference/package-summary  how getraw passs txt to getFlcik?

class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete, GetFlickerJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {
    private val TAG = "MainActivity"

    private val flickerRecyclerViewAdapter = FlickerRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        //conecting adapter. LayoutManager handle layout, cause RV dont
        recycler_view.layoutManager = LinearLayoutManager(this)
        //add touch listner to recycler view
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view, this))
        recycler_view.adapter = flickerRecyclerViewAdapter
    }
    //url create plus shared preferences
    override fun onResume() {
        Log.d(TAG, " onResume")
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        //retriving data
        val queryResult = sharedPref.getString(FLICKER_QUERY, "")

        if (queryResult != null && queryResult.isNotEmpty()) {
            val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne", queryResult,"en-us", true)
            val getRawData = GetRawData(this)
            //the same, but done diffenert like above
            //getRawData.setDownloadCompleteListner(this)
            getRawData.execute(url)
        }
        super.onResume()
    }
    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            val getFlickJsonData = GetFlickerJsonData(this)
            getFlickJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with status $status. Error message is: $data")
        }
    }
    override fun onDataAvailable(data: List<Photo>) {
        flickerRecyclerViewAdapter.loadNewData(data)
    }
    override fun onError(exception: Exception) {
        Log.e(TAG, "onError called with ${exception.message}")
    }
    private fun createUri(baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, ".createUri starts")

        return Uri.parse(baseURL).
        buildUpon().
                //adding thinkg to uri. append...takes care about separating tags
        appendQueryParameter("tags", searchCriteria).
        appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY").
        appendQueryParameter("lang", lang).
        appendQueryParameter("format", "json").
        appendQueryParameter("nojsoncallback", "1").
        build().toString()
    }
    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, ".onItemClick: starts")
        Toast.makeText(this, "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }
    //intent to PhotoDetailsActivity
    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, ".onItemLongClick: starts")
        val photo = flickerRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected called")
        return when (item.itemId) {
            R.id.action_search ->{
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
