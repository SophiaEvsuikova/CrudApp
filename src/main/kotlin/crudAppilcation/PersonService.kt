package crudAppilcation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service

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

    //FIXME методы insert и delete имеют неочивидную и опасную логику при работе в реальном приложении
    // - обычно ожидается, что инсерт делает только вставку, а делит только удаление, getAll() идет в базу и получает все сущности из таблицы,
    // - что снижает производительность приложения
    // - лучше ничего не возвращать или возвращать результат метода update
    fun delete(id: Int): List<Person> {
        db.update("DELETE FROM person WHERE id = ?", id)
        return getAll()
    }

    fun insert(name: String, lastName: String): List<Person>{
        db.update("insert into person(name, lastname) values (?, ?)", name, lastName)
        return getAll()
    }
}