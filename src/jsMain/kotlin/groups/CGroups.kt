package component.lesson

import react.FC
import react.Props
import react.dom.html.ReactHTML.label

external interface GroupProps : Props {
    var groups: List<String>
}

val CGroups = FC<GroupProps>("Groups") { props ->

    props.groups.forEach {
        label { +it }
    }
}