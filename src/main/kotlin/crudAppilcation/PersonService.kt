package crudAppilcation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service


data class Person(val id: Int, val name: String, val lastName: String)

@Service
class PersonService(var db: JdbcTemplate) {

    fun getAll(): List<Person> = db.query("select * from person")
    { rs, _ ->
        Person(rs.getInt("id"),
               rs.getString("name"),
               rs.getString("lastName"))
    }

    fun getById(id: Int): Person? = db.query("select * from person where id = ?", id)
    { rs, _ ->
        Person(rs.getInt("id"),
               rs.getString("name"),
               rs.getString("lastName"))
    }.firstOrNull()

    fun getByName(name: String): Person? = db.query("select * from person where name = ?", name)
    { rs, _ ->
        Person(rs.getInt("id"),
               rs.getString("name"),
               rs.getString("lastName"))

    }.firstOrNull()


    fun getByLastName(lastName: String): Person? = db.query("select * from person where lastname =? ", lastName)
    { rs, _ ->
        Person(rs.getInt("id"),
               rs.getString("name"),
               rs.getString("lastName"))
    }.firstOrNull()

    fun delete(id: Int) {
        db.update("DELETE FROM person WHERE id = ?", id)
    }

    fun insert(name: String, lastName: String) {
        db.update("insert into person(name, lastname) values (?, ?)", name, lastName)
    }
}