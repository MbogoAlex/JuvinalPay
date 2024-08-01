package com.juvinal.pay.db

import android.util.Log
import androidx.room.Delete
import androidx.room.Query
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.User
import com.juvinal.pay.model.dbModel.UserDetails
import kotlinx.coroutines.flow.Flow

interface DBRepository {
    suspend fun insertUser(user: User)
    suspend fun insertMember(member: Member)
    suspend fun getUserDetails(userId: Int): Flow<UserDetails>
    suspend fun deleteUserDetails(
        user: User,
        member: Member
    )
    suspend fun updateMember(member: Member)
    suspend fun updateUser(user: User)

    suspend fun getAppLaunchState(id: Int): AppLaunchStatus
    suspend fun deleteLaunchState(appLaunchStatus: AppLaunchStatus)
    suspend fun updateAppLaunchState(appLaunchStatus: AppLaunchStatus)
    fun getUser(userId: Int): Flow<User>

    fun getUsers(): Flow<List<User>>
    fun getMembers(): Flow<List<Member>>
}

class DBRepositoryImpl(private val appDao: AppDao): DBRepository{
    override suspend fun insertUser(user: User) {
        Log.d("USER_INSERTION", user.toString())
        appDao.insertUser(user)
    }

    override suspend fun insertMember(member: Member) = appDao.insertMember(member)

    override suspend fun getUserDetails(userId: Int): Flow<UserDetails> {
        Log.d("GETTING_USER_WITH_ID", userId.toString())
        return appDao.getUserDetails(userId)
    }

    override suspend fun deleteUserDetails(
        user: User,
        member: Member
    ) = appDao.deleteUserDetails(
        user = user,
        member = member
    )

    override suspend fun updateMember(member: Member) = appDao.updateMember(member)
    override suspend fun updateUser(user: User) = appDao.updateUser(user)
    override suspend fun getAppLaunchState(id: Int): AppLaunchStatus = appDao.getAppLaunchState(id)

    override suspend fun deleteLaunchState(appLaunchStatus: AppLaunchStatus) = appDao.deleteLaunchState(appLaunchStatus)
    override suspend fun updateAppLaunchState(appLaunchStatus: AppLaunchStatus) = appDao.updateAppLaunchState(appLaunchStatus)
    override fun getUser(userId: Int): Flow<User> = appDao.getUser(userId)
    override fun getUsers(): Flow<List<User>> = appDao.getUsers()

    override fun getMembers(): Flow<List<Member>> = appDao.getMembers()

}