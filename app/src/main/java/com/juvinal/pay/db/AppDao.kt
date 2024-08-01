package com.juvinal.pay.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.User
import com.juvinal.pay.model.dbModel.UserDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(
       user: User
    )

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertMember(
       member: Member
   )

   @Query("SELECT * FROM user")
   fun getUsers(): Flow<List<User>>

   @Query("SELECT * FROM member")
   fun getMembers(): Flow<List<Member>>

    @Query("SELECT * FROM user INNER JOIN member ON user.user_id = member.user_id WHERE user.user_id = :userId")
    fun getUserDetails(userId: Int): Flow<UserDetails>


    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUser(userId: Int): Flow<User>
   suspend fun deleteUserDetails(user: User, member: Member) {
       deleteMember(member)
       deleteUser(user)
   }
    @Delete
   suspend fun deleteUser(user: User)
    @Delete
   suspend fun deleteMember(member: Member)
   @Update
   suspend fun updateMember(member: Member)
   @Update
   suspend fun updateUser(user: User)
   @Transaction
   @Query("SELECT * FROM app_launch_state WHERE id = :id")
   suspend fun getAppLaunchState(id: Int): AppLaunchStatus
   @Delete
   suspend fun deleteLaunchState(appLaunchStatus: AppLaunchStatus)
   @Update
   suspend fun updateAppLaunchState(appLaunchStatus: AppLaunchStatus)

}