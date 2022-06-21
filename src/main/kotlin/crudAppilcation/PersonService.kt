package crudAppilcation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service

@Service
class PersonService(val db: JdbcTemplate) {

    fun getAll(): List<Person> = db.query("select * from person") { rs, _ ->
        Person(rs.getString("id"), rs.getString("name"), rs.getString("lastName"))
    }

    fun getById(id: String): Person? = db.query("select * from person where id = ?", id) { rs, _ ->
        Person(rs.getString("id"), rs.getString("name"), rs.getString("lastName"))
    }.firstOrNull()

    fun getByName(name: String): Person? = db.query("select * from person where name = ?", name) { rs, _ ->
        Person(rs.getString("id"), rs.getString("name"), rs.getString("lastName"))
    }.firstOrNull()

    fun getByLastName(lastName: String): Person? = db.query("select * from person where lastname =? ", lastName)
    { rs, _ ->
        Person(rs.getString("id"), rs.getString("name"), rs.getString("lastName"))
    }.firstOrNull()

    fun delete(id: String): List<Person> = db.query("DELETE FROM person WHERE id = ?", id) { resultSet, _ ->
        Person(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getString("lastName"))
    }


    fun post(id: String, name: String, lastName: String){
        db.update("insert into person values (?, ?, ?)", id, name, lastName)
    }



}