package com.technoblizz.roomviewrv.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    //live data digunakan untuk memanage update data
    @Query("SELECT * from tbl_user ORDER BY username ASC")
    fun getAllUser(): LiveData<List<UserEntity>>

    //onConflict = OnConflictStrategy.IGNORE = adalah jika ada username/primary key yang sama maka akan dibiarkan/tidak ditambahkan / ignored
    //@Insert(onConflict = OnConflictStrategy.REPLACE) = jika username ada yg sama maka di overwrite
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM tbl_user")
    suspend fun deleteAll()
}