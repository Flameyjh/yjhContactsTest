package com.yjh.yjhcontactstest

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yjh.yjhcontactstest.databinding.ActivityMainBinding

/*
* ContentProvider的用法一般有两种
* 1. 使用现有的ContentProvider读取和操作相应程序中的数据
* 2. 创建自己的ContentProvider，给程序的数据提供外部访问接口
* */

class MainActivity : AppCompatActivity() {

    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        binding.contactsView.adapter = adapter

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                1
            )
        } else {
            readContacts()
        }

    }

    //获取动态权限。这里其实可以借助PermissionX工具
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //1. 使用现有的ContentProvider读取和操作相应程序中的数据
    private fun readContacts() {
        //查询联系人数据
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null, null ,null, null)?.apply {
            while (moveToNext()){
                //获取联系人姓名
                val displayName = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                //获取联系人手机号
                val number = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}