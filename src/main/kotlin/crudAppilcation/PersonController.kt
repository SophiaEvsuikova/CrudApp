package crudAppilcation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class PersonController {

    @Autowired
    lateinit var personService: PersonService

    @GetMapping("/people")
    fun getAll(): List<Person> {
        return personService.getAll()
    }

    @GetMapping("/person/name/{name}")
    fun getByName(@PathVariable name: String): Person? {
        return personService.getByName(name)
    }

    @GetMapping("/person/lastname/{lastname}")
    fun getByLastName(@PathVariable lastname: String): Person? {
        return personService.getByLastName(lastname)
    }

    @GetMapping("/person/id/{id}")
    fun getById(@PathVariable id: Int): Person? =
        personService.getById(id)

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: Int) {
        personService.delete(id)
    }

    @GetMapping("/person")
    @ResponseBody
    fun insert(@RequestParam name: String, @RequestParam  lastname: String) {
        personService.insert(name, lastname)
    }
}