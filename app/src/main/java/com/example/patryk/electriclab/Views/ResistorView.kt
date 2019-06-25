package com.example.patryk.electriclab.Views

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.patryk.electriclab.R
import pl.patryk.circuit.Resistor


/**
 * TODO: document your custom view class.
 */
open class ResistorView : View {

    companion object {
        const val DataDesc="SingleColorElement"
    }
    var resistance = Resistor()
    var isHorizotal=false

    var positions:Point = Point(0,0)
        set(value) {
            x = value.x.toFloat()
            y = value.y.toFloat()
            field = value
            invalidate()
        }
    var paint = Paint()
    init {
        paint.color = Color.BLACK
        paint.textSize = 40F
        val layoutParams = LinearLayout.LayoutParams(100, 100)
        setLayoutParams(layoutParams)
        setWillNotDraw(false)
        setOnLongClickListener(ResistorLongClick())

    }

    constructor(context: Context) : super(context) {
        init(null, 0)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ResistorView, defStyle, 0
        )

        a.recycle()

    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isHorizotal)
        {
            canvas.drawRect(0F,0F,50F,100F,paint)
            canvas.drawText(resistance.resistanceValue.toString(),0F,100F,paint)
        }
        else
        {
            canvas.drawRect(0F,0F,100F,50F,paint)
            canvas.drawText(resistance.resistanceValue.toString(),0F,100F,paint)
        }
    }
    class ResistorLongClick():View.OnLongClickListener
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
