package com.example.lab1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var choosen = "+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.setOnCheckedChangeListener{ group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                choosen = radio.text.toString()
            }

    }

    fun onEquals(view: View) {
        var number1 = num1.text.toString().toBigDecimalOrNull()
        var number2 = num2.text.toString().toBigDecimalOrNull()
        if(number1 != null && number2 != null) {
            when(this.choosen){
                "+" -> resOutput.setText((number1 + number2).toString())
                "-" -> resOutput.setText((number1 - number2).toString())
                "/" -> resOutput.setText((number1 / number2).toString())
                "*" -> resOutput.setText((number1 * number2).toString())
            }
        } else {
            resOutput.setText("Wrong input format - two numbers expected")
        }
    }


}
