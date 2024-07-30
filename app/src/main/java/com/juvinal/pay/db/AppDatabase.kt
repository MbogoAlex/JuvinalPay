package com.juvinal.pay.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.User

@Database(entities = [User::class, Member::class, AppLaunchStatus::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val dbFile = context.getDatabasePath("JuvinalPay_db")
                val builder = if (!dbFile.exists()) {
                    Room.databaseBuilder(context, AppDatabase::class.java, "JuvinalPay_db")
                        .createFromAsset("database/app_launch.db")
                        .fallbackToDestructiveMigration()
                } else {
                    Room.databaseBuilder(context, AppDatabase::class.java, "JuvinalPay_db")
                        .fallbackToDestructiveMigration()
                }

                builder.build().also { Instance = it }
            }
        }
    }
}