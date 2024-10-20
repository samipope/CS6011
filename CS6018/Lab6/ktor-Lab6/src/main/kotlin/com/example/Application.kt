package com.example

import com.example.models.Posts
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.routes.postRoutes
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.server.response.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Install JSON Content Negotiation
    install(ContentNegotiation) {
        json() // Enables JSON serialization/deserialization using kotlinx-serialization
    }

    // Initialize the in-memory H2 database
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(Posts) // Create the 'Posts' table
    }

    // Set up routing
    routing {
        postRoutes() // All the routes for posts (get, post, delete)
    }
}

