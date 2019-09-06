package com.technoblizz.roomviewrv.Repository

import androidx.lifecycle.LiveData
import com.technoblizz.roomviewrv.Database.UserDao
import com.technoblizz.roomviewrv.Database.UserEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allUser: LiveData<List<UserEntity>> = userDao.getAllUser()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insert(user: UserEntity){
        userDao.insert(user)
    }
}

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#7