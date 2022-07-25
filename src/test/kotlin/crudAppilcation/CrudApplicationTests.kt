package crudAppilcation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CrudApplicationTests {

    val elements : MutableList<Person> = mutableListOf(
        Person(1,"Радж", "Кутропале"),
        Person(2, "Агент", "Купер"),
        Person(3, "Леонард", "Хофстедер"),
        Person(4, "Говард", "Воловиц"),
    )

    val id = 2
    val name = "Агент"
    val lastname = "Купер"

    //FIXME тесты совершенно ничего не проверяют
    // - ассертится значение, которое явно было определено в thenReturn, то есть реальная логика методов не используется
    // - здесь лучше использовать встроенную базу H2 и избавиться от моков

    //FIXME
    // - для юнит тестов используются моки, когда в проверяемом сервисе множестов зависимостей, у которых есть своя логика
    // - тогда создаются моки как раз на эти зависимости и их поведение явно определяется, чтобы обособить их от проверяемого кода
    // - при падении таких тестов будет сразу понятно, что неправильно работает ТОЛЬКО проверяемый сервис
    @Test
    fun getAllTest(){
        // initial parameters
        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.getAll()).thenReturn(elements)

        // action
        val result = mockedObject.getAll()

        // result
        assertEquals(result.count(), elements.count())
        assertSame(result, elements)
    }
    @Test
    fun getByIdTest() {
        // initial parameters
        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.getById(id = id)).thenReturn(elements[id - 1])

        // action
        val result = mockedObject.getById(id)

        // result
        assertEquals(result, elements[id - 1])
        assertSame(result, elements[id - 1])
    }

    @Test
    fun getByNameTest() {
        // initial parameters
        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.getByName(name = name)).thenReturn(elements[1])

        // action
        val result = mockedObject.getByName(name)

        // result
        assertEquals(result, elements[1])
        assertSame(result, elements[1])
    }

    @Test
    fun getByLastNameTest() {
        // initial parameters
        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.getByName(name = lastname)).thenReturn(elements[1])

        // action
        val result = mockedObject.getByName(lastname)

        // result
        assertEquals(result, elements[1])
        assertSame(result, elements[1])
    }

    fun deleteObject(list:MutableList<Person>, id: Int): List<Person> {

        val  element = list.find { it.id == id }

        return if (element == null)
            list
        else {
            elements.remove(element)
            elements
        }
    }

    @Test
    fun deleteTest(){
        // initial parameters
        val originalSize = elements.size
        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.delete(id = id)).thenReturn(deleteObject(elements, id))

        // action
        val result = mockedObject.delete(2)

        // result
        assertEquals(elements.size, originalSize - 1)
        assertSame(elements, result)
    }


    fun insertObj(list:MutableList<Person>, name: String, lastName: String): List<Person> {
        val maxId = elements.maxOfOrNull { it.id }
        if (maxId != null) {
            elements.add(Person(maxId + 1,name, lastname))
        }
        return elements
    }


    @Test
    fun insertTest(){
        // initial parameters
        val originalSize = elements.size

        val mockedObject = mock(PersonService::class.java)
        `when`(mockedObject.insert(name, lastname)).thenReturn(insertObj(elements, name, lastname))

        // action
        val result = mockedObject.insert(name, lastname)

        // result
        assertEquals(elements.size, originalSize + 1)
        assertSame(elements, result)
    }
}
