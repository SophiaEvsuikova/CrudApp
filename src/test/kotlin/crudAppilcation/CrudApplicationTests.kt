package crudAppilcation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:postgresql:testdb"
    ]
)
class CrudApplicationTests(@Autowired val client: TestRestTemplate) {

    @Test
    fun `testing if we can post and retrieve the data`() {
        val id = "6"
        val person = Person(id, "some message","some LastName")
        client.postForObject<Person>("/", person)

        val entity = client.getForEntity<String>("/$id")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(person.id)
        assertThat(entity.body).contains(person.name)

        val msg = client.getForObject<Person>("/$id")!!
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(msg.id).isEqualTo(person.id)
        assertThat(msg.name).contains(person.name)
    }

    @Test
    fun `message not found`() {
        val id = "${Random.nextInt()}".uuid()
        val entity = client.getForEntity<String>("/$id")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }



}
