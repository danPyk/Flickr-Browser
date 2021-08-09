package academy.learnprogramming.flickrbrowser.activity

import academy.learnprogramming.flickrbrowser.R
import academy.learnprogramming.flickrbrowser.data.Photo
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

        //getting data from intent, from photo object
        val photo = intent.getParcelableExtra(PHOTO_TRANSFER) as Photo        //why we have acces to title?
        photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
        photo_tags.text = resources.getString(R.string.photo_tags_text, photo.tags)
        photo_author.text = resources.getString(R.string.photo_author_text, photo.author)
        Picasso.get().load(photo.link)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photo_image)
    }
}