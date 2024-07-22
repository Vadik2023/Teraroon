package com.example.teraroon

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT, count INT, suc INT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User): Boolean {

        if(getUser(user.login, user.pass)) {
            return true
        }
        else {
            val values = ContentValues()
            values.put("login", user.login)
            values.put("email", user.email)
            values.put("pass", user.pass)
            values.put("count", 0)
            values.put("suc", 0)

            val db = this.writableDatabase
            db.insert("users", null, values)
            db.close()

            return false
        }
    }

    fun getUser(login: String, pass: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)

        val answear = result.moveToFirst()

        result.close()
        db.close()

        return answear

    }

    fun getCount(login: String, pass: String): Int {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        result.moveToFirst()
        val count = result.getInt(result.getColumnIndexOrThrow("count"))

        db.close()
        result.close()

        return count
    }

    fun setCount(login: String, pass: String, count: Int) {
        val db = this.writableDatabase

        val result = db.rawQuery("SELECT id FROM users WHERE login = '$login' AND pass = '$pass'", null)
        result.moveToFirst()
        val id = result.getInt(result.getColumnIndexOrThrow("id"))

        val sql = "UPDATE user SET count = ? WHERE id = ?"
        val statement = db.compileStatement(sql)
        statement.bindLong(1, count.toLong())
        statement.bindLong(2, id.toLong())

        db.close()
    }

    fun getProgress(login: String, pass: String): Int {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT suc FROM users WHERE login = '$login' AND pass = '$pass'", null)
        result.moveToFirst()
        val progress = result.getInt(result.getColumnIndexOrThrow("suc"))

        db.close()
        result.close()

        return progress
    }

    fun setProgress(login: String, pass: String, prog: Int) {
        val db = this.writableDatabase

        val result = db.rawQuery("SELECT id FROM users WHERE login = '$login' AND pass = '$pass'", null)
        result.moveToFirst()
        val id = result.getInt(result.getColumnIndexOrThrow("id"))

        val sql = "UPDATE user SET suc = ? WHERE id = ?"
        val statement = db.compileStatement(sql)
        statement.bindLong(1, prog.toLong())
        statement.bindLong(2, id.toLong())

        db.close()
    }
}