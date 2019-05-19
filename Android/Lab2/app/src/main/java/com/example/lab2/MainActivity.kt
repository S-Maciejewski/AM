package com.example.lab2

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    var randomNumber = 0
    var currentScore = 0
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomNumber = (0..20).random()
        Toast.makeText(this@MainActivity, "Nowa liczba wylosowana", Toast.LENGTH_SHORT).show()
        hint.setText("Spróbuj zgadnąć wylosowaną liczbę z przedziału [0, 20]")
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        score = sharedPref.getInt("score", 0)
        record.setText("Twój wynik: " + score)
    }


    fun guessNumber(view: View){
        var num = numberInput.text.toString().toInt()
        if(num != null){
            currentScore++

            if(currentScore == 11){
                val builder = android.app.AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Przegrałeś")
                builder.setPositiveButton("Ok"){dialog, which ->
                    beginNewGame(view)
                }
                val dialog = builder.create()
                dialog.show()
            } else if(num == randomNumber){
                hint.setText("Gratulacje! Wygrałeś po " + currentScore + " próbach!")

                val builder = android.app.AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Gratulacje! Wygrałeś po " + currentScore + " próbach!")
                builder.setPositiveButton("Ok"){dialog, which ->
                    addPoints()
                    beginNewGame(view)
                }

                val dialog = builder.create()
                dialog.show()

            } else if(num > randomNumber){
                hint.setText("Przestrzeliłeś! Spróbuj podać mniejszą liczbę. Do tej pory próbowałeś " + currentScore + " razy.")
            } else {
                hint.setText("To trochę za mało! Spróbuj podać większą liczbę Do tej pory próbowałeś " + currentScore + " razy.")
            }
        }
    }

    fun addPoints(){
        val points = 0
        when(currentScore) {
            1 -> score += 5
            2, 3, 4 -> score += 3
            5, 6 -> score += 2
            7, 8, 9, 10 -> score += 1
        }

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putInt("score", score)
            commit()
        }
        record.setText("Twój wynik: " + score)
        setListAsync(this).execute(score.toString())
    }

    fun beginNewGame(view: View){
        randomNumber = (0..20).random()
        currentScore = 0
        hint.setText("Nowa gra - spróbuj zgadnąć wylosowaną liczbę z przedziału [0, 20]")
        Toast.makeText(this@MainActivity, "Nowa liczba wylosowana", Toast.LENGTH_SHORT).show()
    }

    fun openScoreboard(view: View){
        val scoreboardIntent = Intent(this, scoreboard::class.java)
        startActivity(scoreboardIntent)
    }
}



class setListAsync(private var activity: MainActivity) : AsyncTask<String, String, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {
        val res = URL("http://hufiecgniezno.pl/br/record.php?f=add&id=132275&r=" + params[0]).readText()
        return res
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}