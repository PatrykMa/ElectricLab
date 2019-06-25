package com.example.patryk.electriclab

import android.content.ClipDescription
import android.graphics.Point
import android.util.Log
import android.view.DragEvent
import android.view.View
import com.example.patryk.electriclab.Views.Menu.MenuNodeView
import com.example.patryk.electriclab.Views.Menu.MenuResistorView
import com.example.patryk.electriclab.Views.NodeView
import com.example.patryk.electriclab.Views.ResistorView
import java.lang.Exception

class TouchListener(var view:MainActivity): View.OnDragListener {
    override fun onDrag(v: View, event: DragEvent): Boolean {
        // Defines a variable to store the action type for the incoming event
        val action = event.action
        // Handles each of the expected events
        when (action) {

            DragEvent.ACTION_DRAG_STARTED -> {
                // Determines if this View can accept the dragged data
                return if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    true
                } else false
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                // Applies a GRAY or any color tint to the View. Return true; the return value is ignored.
                //v?.background?.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
                // Invalidate the view to force a redraw in the new tint
                v?.invalidate()
                return true
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                // Ignore the event
                return true

            DragEvent.ACTION_DRAG_EXITED -> {
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //It will clear a color filter .
                // v?.background?.clearColorFilter()
                // Invalidate the view to force a redraw in the new tint
                v?.invalidate()
                return true
            }

            DragEvent.ACTION_DROP -> {
                // Gets the item containing the dragged data
                val item = event.clipData.getItemAt(0)

                val dragData = item.text.toString()

                // v?.background?.clearColorFilter()
                // Invalidates the view to force a redraw
                v?.invalidate()


                //val owner = vw.parent as ViewGroup
                // owner.removeView(vw) //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                //val container = v as ConstraintLayout
                try {
                    when(dragData) {
                        MenuResistorView.DataDesc -> addResistoCase(event)
                        ResistorView.DataDesc -> moveResistor(event)
                        NodeView.DataDesc -> moveNode(event)
                        MenuNodeView.DataDesc -> addNode(event)
                    }

                }catch (e: Exception)
                {
                    var x=0
                }

                // Returns true. DragEvent.getResult() will return true.
                return true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                // Turns off any color tinting
                // v?.background?.clearColorFilter()
                // Invalidates the view to force a redraw
                v?.invalidate()
                // Does a getResult(), and displays what happened.
                if (event.result){}
                //Toast.makeText(, "The drop was handled.", Toast.LENGTH_SHORT).show()
                else{}
                // Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show()
                // returns true; the value is ignored.
                return true
            }
            // An unknown action type was received.
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }

    fun addResistoCase(event:DragEvent)
    {
        view.resistorList = view.resistorList.toMutableList().also {  it.add(
            ResistorView(
                view
            ).also {
            it.positions = Point(event.x.toInt(),event.y.toInt())  }
        )}.toTypedArray()
    }

    fun moveResistor(event: DragEvent)
    {
        var resistor = event.localState as ResistorView
        resistor.positions = Point(event.x.toInt(),event.y.toInt())
    }

    fun addNode(event: DragEvent)
    {
        view.nodeList = view.nodeList.toMutableList().also {
            it.add(NodeView(view).also { it.positions =  Point(event.x.toInt(),event.y.toInt()) })
        }
    }

    fun moveNode(event: DragEvent)
    {
        val node = event.localState as NodeView
        node.positions =  Point(event.x.toInt(),event.y.toInt())
    }
}