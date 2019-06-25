package com.example.patryk.electriclab.Views.Menu

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.view.View
import com.example.patryk.electriclab.Views.NodeView

class MenuNodeView(context: Context):NodeView(context) {
    companion object {
        const val DataDesc="menuNode"

    }
    init {
        setOnLongClickListener(MyLongClick())
        setPadding(15,15,15,15)
    }

    class MyLongClick: View.OnLongClickListener
    {
        override fun onLongClick(v: View): Boolean {
            // Create a new ClipData.Item from the ImageView object's tag
            val item = ClipData.Item(DataDesc)
            // Create a new ClipData using the tag as a label, the plain text MIME type, and
            // the already-created item. This will create a new ClipDescription object within the
            // ClipData, and set its MIME type entry to "text/plain"
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData("tag", mimeTypes, item)
            // Instantiates the drag shadow builder.
            val dragshadow = View.DragShadowBuilder(v)
            // Starts the drag
            v.startDrag(
                data        // data to be dragged
                , dragshadow   // drag shadow builder
                , v           // local data about the drag and drop operation
                , 0          // flags (not currently used, set to 0)
            )
            return true
        }
    }
}