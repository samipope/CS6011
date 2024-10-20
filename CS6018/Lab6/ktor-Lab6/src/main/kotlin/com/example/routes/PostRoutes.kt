package com.example.routes

import com.example.models.Post
import com.example.models.Posts
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant


@Serializable
data class CreatePostRequest(val text: String)

fun Route.postRoutes() {
    get("/posts") {
        val posts = transaction {
            Posts.selectAll().map {
                Post(
                    id = it[Posts.id],
                    text = it[Posts.text],
                    timestamp = it[Posts.timestamp]
                )
            }
        }
        call.respond(posts)
    }

    get("/posts/since/{time}") {
        val time = call.parameters["time"]?.toLongOrNull() ?: 0L
        if (time == 0L) {
            call.respondText("Invalid timestamp", status = HttpStatusCode.BadRequest)
            return@get
        }

        val posts = transaction {
            Posts.select { Posts.timestamp greaterEq time }.map {
                Post(it[Posts.id], it[Posts.text], it[Posts.timestamp])
            }
        }
        call.respond(posts)
    }


    get("/posts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            return@get
        }

        val post = transaction {
            Posts.select { Posts.id eq id }.singleOrNull()?.let {
                Post(it[Posts.id], it[Posts.text], it[Posts.timestamp])
            }
        }

        if (post == null) {
            call.respondText("Post not found", status = HttpStatusCode.NotFound)
        } else {
            call.respond(post)
        }
    }

    post("/posts") {
        val request = call.receive<CreatePostRequest>()
        val postId = transaction {
            Posts.insert {
                it[text] = request.text
                it[timestamp] = Instant.now().epochSecond
            } get Posts.id
        }
        call.respond(mapOf("postId" to postId))
    }


    delete("/posts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            return@delete
        }

        val deleted = transaction {
            Posts.deleteWhere { Posts.id eq id }
        }

        if (deleted > 0) {
            call.respondText("Post deleted successfully")
        } else {
            call.respondText("Post not found", status = HttpStatusCode.NotFound)
        }
    }
}
