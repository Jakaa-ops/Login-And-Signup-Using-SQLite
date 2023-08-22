package com.jakaa.loginandsignupusingsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Step 2: Extend all the SQLite helper
class DatabaseHelper(private val context: Context):
SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //Step 1: Declare all the constants.
    companion object{
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Step 3: Create Table using query
        val createTableQuery = ("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Step 4: Drop the table
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    //Step 5: Insert the new user
    fun insertUser(username: String, password: String): Long{
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    //Step 6: Read the user
    fun readUser(username: String, password: String): Boolean{
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

}




































































