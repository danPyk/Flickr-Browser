package academy.learnprogramming.flickrbrowser.activity

import academy.learnprogramming.flickrbrowser.R
import android.view.View
import androidx.appcompat.app.AppCompatActivity
//4 key purpose
internal const val FLICKER_QUERY = "FLICK_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

//class 4 handling clicking home button, extend to all 3 activities
open class BaseActivity: AppCompatActivity() {

    internal fun activateToolbar(enableHome: Boolean){
        var toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar)
        //supp. - Retrieve a reference to this activity's ActionBar. setD... - Set whether home should be displayed as an "up" affordance.
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}