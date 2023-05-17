package me.ositlar.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title("WebApp")
        link {
            rel = "stylesheet"
            href = "static/style.css"
        }
        link {
            rel = "icon"
            href = "data:,"
        }
    }
    body {
        div {
            id = "root"
            +"React will be here!!"
        }
        script(src = "/static/parsing.js") {}
    }
}

fun Application.static() {
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        static("/static") {
            resources()
        }
    }
}