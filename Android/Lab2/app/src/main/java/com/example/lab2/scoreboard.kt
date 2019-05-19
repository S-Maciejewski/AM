package com.example.lab2

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scoreboard.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class scoreboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        textView.text = parseResponse(getListAsync(this).execute().get())


    }

    fun closeScoreboard(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun parseResponse(res: String) : String{
        var scoreList = "Najlepsze wyniki\n"
        var json = JSONArray(res)
        for (i in 0 until 10){
            var record = JSONArray(json.get(i).toString())
            scoreList += record.get(1).toString() + " - " + record.get(2) + "\n"
        }
        return scoreList
    }
}

class getListAsync(private var activity: scoreboard) : AsyncTask<String, String, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {
        val res = URL("http://hufiecgniezno.pl/br/record.php?f=get").readText()
        return res
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}
