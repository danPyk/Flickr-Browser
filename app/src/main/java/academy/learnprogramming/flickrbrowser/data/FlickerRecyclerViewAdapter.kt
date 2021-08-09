package academy.learnprogramming.flickrbrowser.data

import academy.learnprogramming.flickrbrowser.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

//inner class used with recycler can provide memory leaks, move class at start of class
//main activity provide our adapter list of photo. we store them in photoList const parameter

//just coiatins propoerites to store image view and thext view, that ll hold data
class FlickerImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class FlickerRecyclerViewAdapter(private var photoList: List<Photo>): RecyclerView.Adapter<FlickerImageViewHolder>() {
    //TAG can be max 23 characters
    private val TAG = "FlickerRecycViewAda"

    //called by RV when wants new data stored in view holder
    override fun onBindViewHolder(holder: FlickerImageViewHolder, position: Int) {
        //4 handling no results
        if(photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder)
            holder.title.text = "No photos match given criteria"
        }else{
            //recyclerView gives us position parameter to you know
            val photoItem = photoList[position]
            //picasso is sigeltone, so we not using new operator. Alse works na bckd fhread
            //load loads image frome orl, and strore in image frome Photo class. How TH image is accesilbe here?
            Picasso.get().load(photoItem.image)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into((holder.thumbnail))
            holder.title.text = photoItem.title

        }

    }//check if theres any data to display
    override fun getItemCount(): Int {
        //num of photos in list. if works like expression
        return if(photoList.isNotEmpty()) photoList.size else 1
    }
    fun loadNewData(newPhotos: List<Photo>){
        photoList = newPhotos
        //grabs list and copy to new field. Says recycler view when to upadate data
        notifyDataSetChanged()
    }
    //full pic aftert clicking
    fun getPhoto(position: Int): Photo?{
        return if(photoList.isNotEmpty()) photoList[position] else null
    }
    //inflate view frome browse, then return that view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickerImageViewHolder {
    Log.d(TAG, "onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickerImageViewHolder(view)
    }
}