package crudAppilcation

//FIXME лучше такие небольшие классы определять рядом с местом, где они используются
// - например в PersonService
// - все таки это на Java, где каждый класс надо определять в отдельном файле
data class Person(val id: Int, val name: String, val lastName: String)
