package academy.learnprogramming.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by timbuchalka for Android Oreo with Kotlin course
 * from www.learnprogramming.academy
 */
class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener() {

    private val TAG = "RecyclerItemClickListen"

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }//anonymus cllas which extends gestrue listners

    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        //Notified when a tap occurs with the up MotionEvent that triggered it.
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp starts")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null) {
                Log.d(TAG, ".onSingleTapUp calling listener.onItemClick")
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }
        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress starts")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onLongPress calling listener")
            if (childView != null) {
                listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
        }
    })
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.e(TAG, "onInterrupt clled $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, ".onInterceptTouchEvent() returning: $result")
//        return super.onInterceptTouchEvent(rv, e)
        return result
    }
}