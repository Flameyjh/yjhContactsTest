package com.yjh.yjhcontactstest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

//2. 创建自己的ContentProvider，给程序的数据提供外部访问接口
class MyProvider: ContentProvider() {

    private val table1Dir = 0
    private val table1Item = 1
    private val table2Dir = 2
    private val table2Item = 3

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI("com.example.app.provider", "table1", table1Dir) //某表
        uriMatcher.addURI("com.example.app.provider", "table1/#", table1Item) //某表的某行
        uriMatcher.addURI("com.example.app.provider", "table2", table2Dir)
        uriMatcher.addURI("com.example.app.provider", "table2/#", table2Item)
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        when(uriMatcher.match(uri)){
            table1Dir -> {
                //查询table1表中的所有数据
            }
            table1Item -> {
                //查询table1表中的单条数据
            }
            table2Dir -> {
                //查询table2表中的所有数据
            }
            table2Item -> {
                //查询table2表中的单条数据
            }
        }
        return null
    }

    override fun getType(uri: Uri): String? = when(uriMatcher.match(uri)){
        table1Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table1"
        table1Item -> "vnd.android.cursor.item/vnd.com.example.app.provider.table1"
        table2Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table2"
        table2Item -> "vnd.android.cursor.item/vnd.com.example.app.provider.table2"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}