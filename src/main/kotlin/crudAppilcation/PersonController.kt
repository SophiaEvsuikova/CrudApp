package crudAppilcation

import org.springframework.web.bind.annotation.*

@RestController
class PersonController(val personService: PersonService) {

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
    fun getById(@PathVariable id: String): Person? =
        personService.getById(id)

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: String){
        personService.delete(id)
    }

    @GetMapping("/person")
    @ResponseBody
    fun post(@RequestParam id: String, @RequestParam name: String,@RequestParam  lastname: String) {
        personService.post(id, name, lastname)
    }
}