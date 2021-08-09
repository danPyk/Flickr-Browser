package academy.learnprogramming.flickrbrowser.data

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import java.io.IOException
import java.io.ObjectStreamException

/**
 * Created by timbuchalka for Android Oreo with Kotlin course
 * from www.learnprogramming.academy
 */
@Parcelize
class Photo(var title: String, var author: String, var authorId: String, var link: String, var tags: String, var image: String): Parcelable {
    companion object{
        //different versions of java generets different UID
        private const val serialVersionUID = 1L
    }


}