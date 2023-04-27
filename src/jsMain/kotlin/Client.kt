import react.FC
import react.Props
import react.create
import react.dom.client.createRoot

fun main() {
    val container = web.dom.document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props> {

}