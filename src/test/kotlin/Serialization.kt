import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.serialization.GroupSerializer.serialize
import fr.shyrogan.konfigurate.serialization.GroupSerializer.deserialize
import fr.shyrogan.konfigurate.setting
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

fun main() {
    val serialization = Serialization()
    val text = serialization.serialize()
    println(text)
    serialization.deserialize(text.replace("false", "true"))
    println(serialization.subSub)
}

class Serialization: Group {

    override val identifier = "Serialization"
    override val subGroups: MutableList<Group> = LinkedList()

    val parent by setting("my.setting", "desc", true)

    val sub by setting("my.sub", "desc", Instant.now()) {
        childOf(Serialization::parent)
    }

    val subSub by setting("my.sub.sub", "desc", false) {
        childOf(Serialization::sub)
    }

    val group = object: Group {
        override val identifier = "group"
        override val subGroups = LinkedList<Group>()
    }

}