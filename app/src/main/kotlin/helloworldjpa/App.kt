@file:Suppress("RemoveRedundantQualifierName")

package helloworldjpa

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

class App {
    val greeting: String
        get() {
            val emFactory = jakarta.persistence.Persistence.createEntityManagerFactory("helloworld")
            val em = emFactory.createEntityManager()
            em.transaction.begin()
            val msg = Message(text = "Claus Polanka")
            em.persist(msg)
            em.transaction.commit()

            em.transaction.begin()
            val messages = em.createQuery("select m from Message m", Message::class.java).resultList
            messages.last().text = "updated"
            em.transaction.commit()
            em.close()
            emFactory.close()

            return "Hello World to '${msg.text}'!"
        }
}

fun main() {
    println(App().greeting)
}

@Entity
class Message(
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
