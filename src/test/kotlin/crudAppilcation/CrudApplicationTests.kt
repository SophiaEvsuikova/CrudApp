package crudAppilcation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

class MyPostgreSQLContainer(imageName: String) : PostgreSQLContainer<MyPostgreSQLContainer>(imageName)

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)

@Testcontainers
class CrudApplicationTests {

    @Autowired
    val client: TestRestTemplate = TestRestTemplate()

    @Autowired
    lateinit var personService: PersonService

    @Autowired
    val jdbc: JdbcTemplate = JdbcTemplate()

    companion object{
        @Container
        val container: MyPostgreSQLContainer = MyPostgreSQLContainer("postgres:latest")
    }

    @AfterEach
    fun cleanup() {
        jdbc.execute("truncate table person")
    }

    val name = "Александр"
    val lastname = "Пушкин"

    @Test
    fun testContainerRunning() {
        assertTrue(container.isRunning)
    }

    @Test
    fun testGetPeople () {
        // arrange
        client.getForEntity<String>("/person?name=$name&lastname=$lastname")
        val people = personService.getAll()

        // act
        val entity = client.getForEntity<String>("/people")

        // assert
        assertThat(entity.body).contains(people[0].name,people[0].lastName)
    }

    @Test
    fun testGetByName() {
        // arrange
        client.getForEntity<String>("/person?name=$name&lastname=$lastname")

        // act
        val entity = client.getForEntity<String>("/person/name/$name")

        // assert
        assertThat(entity.body).contains(name)
    }

    @Test
    fun testGetByLastName() {
        // arrange
        client.getForEntity<String>("/person?name=$name&lastname=$lastname")

        // act
        val entity = client.getForEntity<String>("/person/lastname/$lastname")

        // assert
        assertThat(entity.body).contains(lastname)
    }

    @Test
    fun testInsertPeople() {
        // arrange
        client.getForEntity<String>("/person?name=$name&lastname=$lastname")

        // act
        val people = client.getForEntity<String>("/people")

        // assert
        assertThat(people.body).contains(name, lastname)
    }

    @Test
    fun testDeletePeople() {
        // arrange
        client.getForEntity<String>("/person?name=$name&lastname=$lastname")
        val person = personService.getByName(name)!!

        // act
        client.delete("/delete?id=" + person.id)
        val emptyPerson = personService.getById(person.id)

        // assert
        assertNull(emptyPerson)
    }
}
