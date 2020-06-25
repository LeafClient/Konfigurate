import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.serialization.GroupSerializer.serialize
import fr.shyrogan.konfigurate.serialization.GroupSerializer.deserialize
import fr.shyrogan.konfigurate.setting
import java.time.Instant
import java.time.temporal.ChronoUnit

fun main() {
    val serialization = Serialization()
    val text = serialization.serialize()
    println(text)
    serialization.deserialize(text.replace("false", "true"))
}

class Serialization: Group {

    override val label = "Serialization"
    override val subGroups = mutableListOf<Group>()

    val parent by setting("My setting", true)

    val sub by setting("My sub", Instant.now()) {
        childOf(Serialization::parent)
    }

    val subSub by setting("My sub sub", false) {
        childOf(Serialization::sub)
    }

}