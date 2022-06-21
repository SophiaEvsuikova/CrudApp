package crudAppilcation

/*
@WebMvcTest
class WebTierTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var service: PersonService

    @Test
    fun `find messages`() {
        every { service.getAll() } returns listOf(Person("1", "First"),
        Person("2", "Second"))

        mockMvc.get("/")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect {
                jsonPath("\$.[0].id") { value("1") }
                jsonPath("\$.[0].text") { value("First") }
                jsonPath("\$.[1].id") { value("2") }
                jsonPath("\$.[1].text") { value("Second") }
            }

        verify(exactly = 1) { service.getAll() }
    }
}

 */