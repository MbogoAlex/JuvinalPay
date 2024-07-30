package com.juvinal.pay.model.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "user", [Index(value = ["user_id"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val user_id: Int,
    val surname: String,
    val fname: String,
    val lname: String,
    val password: String,
    val document_type: String,
    val document_no: String,
    val email: String,
    val phone_no: String,
    val user_status: Int,
    val created_at: String,
    val updated_at: String,
    val name: String,
    val uid: String,

)
@Entity(tableName = "member", foreignKeys = [
    ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)
], indices = [Index(value = ["user_id"], unique = true)])
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val user_id: Int,
    val mem_no: String?,
    val mem_joined_date: String?,
    val mem_status: Int?,
    val created_at: String?,
    val updated_at: String?
)
@Entity(tableName = "app_launch_state")
data class AppLaunchStatus(
    @PrimaryKey
    val id: Int = 0,
    val launched: Int = 0,
    val user_id: Int? = null
)

data class UserDetails(
    @Embedded val member: Member = Member(0, 0, "", "", 0, "", ""),
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val user: User = User(0, 0, "", "", "", "", "", "", "", "", 0, "", "", "", ""),
)

