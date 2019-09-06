package com.technoblizz.roomviewrv.AddUser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.technoblizz.roomviewrv.R
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUser : AppCompatActivity() {

    private lateinit var et_username: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        et_username = findViewById(R.id.et_username)

        val btn_save = findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(et_username.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                val username = et_username.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, username)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.technoblizz.roomviewrv.REPLY"
    }
}
