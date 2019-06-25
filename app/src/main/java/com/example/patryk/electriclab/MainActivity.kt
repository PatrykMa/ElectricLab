package com.example.patryk.electriclab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import com.example.patryk.electriclab.Views.Menu.MenuNodeView
import com.example.patryk.electriclab.Views.Menu.MenuResistorView
import com.example.patryk.electriclab.Views.NodeView
import com.example.patryk.electriclab.Views.ResistorView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var resistanceInput: View = inflate(this,R.layout.resistance_input, null);



    var selectetView:View? = null
        set(value) {

                try {
                    val resistor = value as ResistorView
                    resistor.resistance.resistanceValue =resistanceInput.findViewById<EditText>(R.id.editText_res).text.toString().toDouble()
                }catch (e:Exception){}
            
            field = value

        }
    var menuItems = mutableListOf<View>()

    var resistorList= arrayOf<ResistorView>()
        set(value) {
            field = value
            drawAll()
        }

    var nodeList = mutableListOf<NodeView>()
        set(value) {
            field = value
            drawAll()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_view.setOnDragListener(TouchListener(this))
        menuItems.add(MenuResistorView(this))
        menuItems.add(MenuNodeView(this))
        resistanceInput.findViewById<Button>(R.id.button_confirm).setOnClickListener {
            selectetView = null
        }

    }

    fun drawAll()
    {
        main_view.removeAllViews()
        resistorList.forEach {
            main_view.addView(it)

        }
        nodeList.forEach {
            main_view.addView(it)
        }

    }
    fun drawMeu()
    {
        circuit_items.removeAllViews()
        menuItems.forEach {
            circuit_items.addView(it)
        }
    }

    override fun onResume() {
        super.onResume()
        drawMeu()
        circuit_items.invalidate()
    }
}
