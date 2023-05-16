@file:Suppress("CAST_NEVER_SUCCEEDS")

import component.lesson.CGroupContainer
import kotlinx.browser.document
import react.FC
import react.Props
import react.create
import react.createContext
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import tanstack.query.core.QueryClient
import tanstack.query.core.QueryKey
import tanstack.react.query.QueryClientProvider
import tanstack.react.query.devtools.ReactQueryDevtools
import web.html.HTMLElement
import react.router.dom.Link as Link

val invalidateRepoKey = createContext<QueryKey>()

fun main() {
    val container = document.getElementById("root") as HTMLElement
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            Link{
                +"Группы"
                to = Config.groupPath
            }
            Routes{
                Route{
                    path = Config.groupPath
                    element = CGroupContainer.create()
                }
            }


            ReactQueryDevtools { }
        }
    }
}