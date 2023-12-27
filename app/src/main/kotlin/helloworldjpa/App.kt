@file:Suppress("RemoveRedundantQualifierName")

package helloworldjpa

import jakarta.persistence.*
import jakarta.validation.constraints.*

class App {
    val greeting: String
        get() {
            val emFactory = jakarta.persistence.Persistence.createEntityManagerFactory("helloworld-mysql")
            var em = emFactory.createEntityManager()
            em.transaction.begin()
            val msg = Message(text = "Claus Polanka")
            em.persist(msg)
            em.transaction.commit()
            em.close()

            em = emFactory.createEntityManager()
            em.transaction.begin()
            val loaded = em.find(Message::class.java, msg.id)
            loaded.text = "updated"
            em.transaction.commit()
            em.close()

            emFactory.close()

            return "Hello World to '${loaded.text}'!"
        }
}

fun main() {
    println(App().greeting)
}

@Entity
data class Message(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @field:NotNull
    var text: String?,
) {
    override fun toString(): String {
        return "Message(id=$id, text=$text)"
    }
}
