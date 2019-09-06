package com.technoblizz.roomviewrv.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        //hapus scope jika tidak menggunakan coroutine
        fun getDatabase(context: Context, scope: CoroutineScope): UserRoomDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        }

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#6


        private class UserDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.userDao())
                    }
                }
            }
        }


        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(userDao: UserDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            // Delete all content here.
//            userDao.deleteAll()

            // Add sample words.
            //sample 1
            var user = UserEntity("Edwin")
            userDao.insert(user)

            //sample 2
            user = UserEntity("Kurnia")
            userDao.insert(user)

        }
    }
}

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#11
