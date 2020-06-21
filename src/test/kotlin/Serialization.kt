import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.serialization.GroupSerializer.serialize
import fr.shyrogan.konfigurate.serialization.GroupSerializer.deserialize

fun main() {
    val serialization = Serialization()
    val text = serialization.serialize()
    println(text)
    serialization.deserialize(text.replace("false", "true"))
    println(serialization.subSub)
}

class Serialization: Group("Serialization") {

    val parent by setting("My setting", true)

    val sub by setting("My sub", false) {
        childOf(Serialization::parent)
    }

    val subSub by setting("My sub sub", false) {
        childOf(Serialization::sub)
    }

}