package com.example.lab2

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBService(context: Context?) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    init {
        service = this
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = "CREATE TABLE USER (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LOGIN VARCHAR(50)," +
                "PASSWORD VARCHAR(50)," +
                "SCORE INTEGER)"

        val createLoggedInUserTable = "CREATE TABLE LOGGED_IN" +
                "(ID INTEGER)"

        db?.execSQL(createUsersTable)
        db?.execSQL(createLoggedInUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS USER")
        db?.execSQL("DROP TABLE IF EXISTS LOGGED_IN")
        onCreate(db)
    }

    fun addUser(login: String, password: String) {
        val db = this.writableDatabase
        val usersNum = DatabaseUtils.queryNumEntries(db, "USER", "LOGIN = ?", arrayOf(login))
        Log.d("addUser", "${usersNum} użytkowników o loginie ${login}")

        if (usersNum == 0L) {
            val values = ContentValues().apply {
                put("LOGIN", login)
                put("PASSWORD", password)
                put("SCORE", 0)
            }
            db.insert("USER", null, values)
            db.close()
            Log.d("addUser", "Dodano użytkownika o loginie ${login}")
        } else {
            Log.d("addUser", "Istnieje już użytkownik o takim loginie")
        }
    }

    fun updateUser(score: Int) {
        val db = this.writableDatabase

        val userId = getLoggedInID()

        db.update(
            "USER",
            ContentValues().apply { put("SCORE", score) },
            "ID LIKE ?",
            arrayOf(userId.toString())
        )

        db.close()
        Log.d("updateUser", "${userId} ma teraz score = ${score}")
    }

    fun loginOrRegisterUser(login: String, password: String) {
        val db = this.readableDatabase

        val select = "LOGIN = ? AND PASSWORD = ?"
        val arr = arrayOf(login, password)
        var userId = -1
        val dbCursor = db.query("USER", null, select, arr, null, null, null)


        if (dbCursor.moveToFirst()) {
            userId = dbCursor.getInt(dbCursor.getColumnIndex("ID"))
        }
        dbCursor.close()
        Log.d("login", "Użytkownik o ID ${userId} - login ${login}")

        if (userId == -1) {
            Log.d("login", "Nowy użytkownik")
            addUser(login, password)
        } else {
            Log.d("login", "Istniejący użytkownik")
            val values = ContentValues().apply {
                put("ID", userId)
            }
            db.insert("LOGGED_IN", null, values)
            Log.d("login", "Zalogowano użytkownika o ID ${userId}")
        }
    }

    fun getScore(): Int {
        val db = this.readableDatabase
        val userId = getLoggedInID()
        var score = 0

        val dbCursor = db.rawQuery("SELECT SCORE FROM USER WHERE ID = ${userId}", null)
        if (dbCursor.moveToFirst()) {
            score = dbCursor.getInt(dbCursor.getColumnIndex("SCORE"))
        }
        return score
    }

    fun getLoggedInID(): Int {
        val db = this.readableDatabase
        val dbCursor = db.rawQuery("SELECT ID FROM LOGGED_IN", null)
        var userId = -1
        if (dbCursor.moveToFirst()) {
            userId = dbCursor.getInt(dbCursor.getColumnIndex("ID"))
        }
        dbCursor.close()

        return userId
    }

    fun getName() : String {
        val db = this.readableDatabase
        val userId = getLoggedInID()
        var name = ""

        val dbCursor = db.rawQuery("SELECT LOGIN FROM USER WHERE ID = ${userId}", null)
        if (dbCursor.moveToFirst()) {
            name = dbCursor.getString(dbCursor.getColumnIndex("SCORE"))
        }
        return name
    }

    fun logout() {
        val db = this.writableDatabase

        db?.execSQL("DROP TABLE IF EXISTS LOGGED_IN")
        val createLoggedInUserTable = "CREATE TABLE LOGGED_IN" +
                "(ID INTEGER)"
        db?.execSQL(createLoggedInUserTable)
    }

    companion object {
        lateinit var service: DBService

        private var dbVersion = 7
        private const val dbName = "lab2.db"

    }
}