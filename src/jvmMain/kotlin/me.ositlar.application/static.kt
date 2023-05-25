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
            rel = "icon"
            href = "data:,"
        }
        link {
            rel = "stylesheet"
            href = "static/style.css"
        }
        link {
            rel = "stylesheet"
            href = "static/header.css"
        }
        link {
            rel = "stylesheet"
            href = "static/footer.css"
        }
        link {
            rel = "stylesheet"
            href = "static/pdf.css"
            type = "text/css"
            media = "print"
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