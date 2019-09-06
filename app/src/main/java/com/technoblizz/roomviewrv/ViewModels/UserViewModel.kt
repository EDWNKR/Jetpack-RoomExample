package com.technoblizz.roomviewrv.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.technoblizz.roomviewrv.Database.UserEntity
import com.technoblizz.roomviewrv.Database.UserRoomDatabase
import com.technoblizz.roomviewrv.Repository.UserRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class UserViewModel(application: Application) : AndroidViewModel(application){

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository

    // LiveData gives us updated words when they change.
    val allUser: LiveData<List<UserEntity>>

    init {
        //inisialisasinya
        val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)
        allUser = repository.allUser
    }

    fun insert(user: UserEntity) = viewModelScope.launch {
        repository.insert(user)
    }

}

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#8