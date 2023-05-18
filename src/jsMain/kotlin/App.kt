@file:Suppress("CAST_NEVER_SUCCEEDS")

import component.lesson.CFlow
import component.lesson.CGroups
import csstype.ClassName
import kotlinx.browser.document
import react.FC
import react.Props
import react.create
import react.createContext
import react.dom.client.createRoot
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.main
import react.dom.html.ReactHTML.nav
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import tanstack.query.core.QueryClient
import tanstack.query.core.QueryKey
import tanstack.react.query.QueryClientProvider
import teachers.CListTeachers
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
            header {
                className = ClassName("header")
                nav{
                    className = ClassName("menu")
                    ReactHTML.ul {
                        className = ClassName("menu__ul")
                        listOf("Cathedra", "Flow", "Teachers").forEach { tag ->
                            li {
                                className = ClassName("menu__li")
                                Link {
                                    +tag
                                    to = tag.lowercase()
                                }
                            }
                        }
                    }
                }
            }

            Routes{
                Route {
                    path = "${Config.flowPath}*"
                    element = CFlow.create()
                }
                Route{
                    path = "${Config.teachersPath}*"
                    element = CListTeachers.create()
                }

            }
            Routes {
                Route {
                    path = ":streamName"
                    element = CGroups.create()
                }
            }

            div{
                className = ClassName("wrapper")
                main {
                    className = ClassName("main")
                    footer{
                        className = ClassName("footer")
                        div{
                            className = ClassName("footer__left")
                            div {
                                className = ClassName("text")
                                +"Разработано студентами группы 20з: "
                                +"Кондратьев, Кадыков, Куриляк"
                            }
                        }
                        div {
                            className = ClassName("footer__right")
                            div{
                                className = ClassName("contact")
                                +""
                            }
                            div{
                                className = ClassName("phone")
                                +"Здесь могла быть ваша реклама"
                            }
                            div{
                                className = ClassName("address")
                                +"Omsk, Карла Маркса просп., 35"
                            }
                        }
                        div{
                            className = ClassName("line")
                        }
                        div{
                            className = ClassName("footer__bottom")
                            +"© 2023"
                        }
                    }
                }
            }
        }
    }
}




