@file:Suppress("CAST_NEVER_SUCCEEDS")

import component.lesson.CFlow
import kotlinx.browser.document
import react.FC
import react.Props
import react.create
import react.createContext
import react.dom.client.createRoot
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import tanstack.query.core.QueryClient
import tanstack.query.core.QueryKey
import tanstack.react.query.QueryClientProvider
import tanstack.react.query.devtools.ReactQueryDevtools
import web.html.HTMLElement

val invalidateRepoKey = createContext<QueryKey>()

fun main() {
    val container = document.getElementById("root") as HTMLElement
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            ReactHTML.ul {
                listOf("Flow", "Cathedra", "Teachers").map { tag ->
                    ReactHTML.li {
                        Link {
                            +tag
                            to = tag.lowercase()
                        }
                    }
                }
            }
            Routes{
                Route{
                    path = Config.flowPath
                    element = CFlow.create()
                }
            }

            ReactQueryDevtools { }

        }
    }
}
