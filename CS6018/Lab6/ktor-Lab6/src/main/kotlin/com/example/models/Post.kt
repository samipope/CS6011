package com.example.models
import kotlinx.serialization.Serializable


import org.jetbrains.exposed.sql.Table
@Serializable
data class Post(val id: Int, val text: String, val timestamp: Long)

object Posts : Table() {
    val id = integer("id").autoIncrement()
    val text = varchar("text", 255)
    val timestamp = long("timestamp")

    override val primaryKey = PrimaryKey(id)
}