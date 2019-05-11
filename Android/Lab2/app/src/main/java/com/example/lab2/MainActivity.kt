package com.example.lab2

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var randomNumber = 0
    var score = 0
    var topScore = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomNumber = (0..20).random()
        Toast.makeText(this@MainActivity, "Nowa liczba wylosowana", Toast.LENGTH_SHORT).show()
        hint.setText("Spróbuj zgadnąć wylosowaną liczbę z przedziału [0, 20]")
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        topScore = sharedPref.getInt("topScore", 99)
        record.setText("Twój najlepszy wynik: " + topScore)
    }

    fun guessNumber(view: View){
        var num = numberInput.text.toString().toInt()
        if(num != null){
            score++
            if(num == randomNumber){
                hint.setText("Gratulacje! Wygrałeś z wnikiem " + score)

                val builder = android.app.AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Gratulacje, wygrałeś z wynikiem " + score)
                builder.setPositiveButton("Ok"){dialog, which ->
                    if(score < topScore)
                        topScore = score
                    val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

                    with(sharedPref.edit()){
                        putInt("topScore", topScore)
                        commit()
                    }

                    beginNewGame(view)
                }

                val dialog = builder.create()
                dialog.show()

            } else if(num > randomNumber){
                hint.setText("Przestrzeliłeś! Spróbuj podać mniejszą liczbę")
            } else {
                hint.setText("To trochę za mało! Spróbuj podać większą liczbę")
            }
        }
    }

    fun beginNewGame(view: View){
        randomNumber = (0..20).random()
        score = 0
        hint.setText("Nowa gra - spróbuj zgadnąć wylosowaną liczbę z przedziału [0, 20]")
        Toast.makeText(this@MainActivity, "Nowa liczba wylosowana", Toast.LENGTH_SHORT).show()
    }
}
