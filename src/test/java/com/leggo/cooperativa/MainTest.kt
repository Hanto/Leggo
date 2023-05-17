package com.leggo.cooperativa

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Testcontainers
class MainTest
{
    companion object
    {
        @JvmStatic @Container
        var mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5.0")
            .withExposedPorts(27017)

        @JvmStatic @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry): Unit =
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }

        @JvmStatic @BeforeAll
        fun beforeAll() : Unit = mongoDBContainer.start()
    }

    @Test
    fun pim()
    {
        while (true) {
        }
    }
}
