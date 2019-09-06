package com.technoblizz.roomviewrv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technoblizz.roomviewrv.Adapters.UserListAdapter
import com.technoblizz.roomviewrv.AddUser.AddUser
import com.technoblizz.roomviewrv.Database.UserEntity
import com.technoblizz.roomviewrv.ViewModels.UserViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newUserActivityRequestCode = 1
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UserListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        userViewModel.allUser.observe(this, Observer {
            users ->
            // Update the cached copy of the words in the adapter.
            users?.let { adapter.setUser(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddUser::class.java)
            startActivityForResult(intent, newUserActivityRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newUserActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val user = UserEntity(data.getStringExtra(AddUser.EXTRA_REPLY))
                userViewModel.insert(user)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Kosong",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}
