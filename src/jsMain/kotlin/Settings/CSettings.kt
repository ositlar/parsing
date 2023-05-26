package Settings

import Config
import js.core.get
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import web.html.InputType
import kotlin.js.json

val CSettings = FC<Props>("Settings") {
    val queryKey = arrayOf("Settings").unsafeCast<QueryKey>()
    val queryClient = useQueryClient()
    var res by useState("")
    val querySettings = useQuery<String, QueryError, String, QueryKey>(
        queryKey = queryKey,
        queryFn = {
            fetchText(Config.editUrlsPath)
        }
    )

    val updateMutation = useMutation<HTTPResult, Any, List<String>, Any>(
        mutationFn = { item: List<String> ->
            fetch(Config.editUrlsPath,
                jso {
                    method = "POST"
                    headers = json(
                        "Content-Type" to "application/json"
                )
                body = Json.encodeToString(item)
            })
        }, options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(queryKey)
            }
        })

    div {
        input {
            type = InputType.file
            onChange = { event ->
                event.target.files!![0].text().then { res = it }
                //console.log(res)
            }
        }
        button {
            +"Отправить"
            onClick = {
                updateMutation.mutateAsync(res.split("\r\n"), null)
            }
        }
    }
}