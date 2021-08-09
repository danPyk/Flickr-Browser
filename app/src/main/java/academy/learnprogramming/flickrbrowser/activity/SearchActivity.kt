package academy.learnprogramming.flickrbrowser.activity

import academy.learnprogramming.flickrbrowser.R
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.preference.PreferenceManager


class SearchActivity : BaseActivity()  {
    private var searchView: SearchView? = null
    private var TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        activateToolbar(true)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        // searchManager privides acces to system search serv
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        Log.d(TAG, "onCreateOptionsMenu $componentName hint is ${searchView?.queryHint} $searchableInfo")
        //allows directly open search tab
        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit called")

                val sheredPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                //chang sheredPref to writable state
                sheredPref.edit().putString(FLICKER_QUERY, query).apply()
                //fix strage behavior on ecterney keybord
                searchView?.clearFocus()

                finish()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })//handing pressing x with open keybord
        searchView?.setOnCloseListener {
            finish()
            false
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}