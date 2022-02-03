package ru.fit_changes.backend.app.ktor.product.configs

import io.ktor.server.application.*

// not yet used anywhere
// as an example

data class CassandraConfig(
    val hosts: String = "localhost",
    val port: Int = 9042,
    val user: String = "cassandra",
    val pass: String = "cassandra",
    val keyspace: String = "test_keyspace"
) {
    constructor(environment: ApplicationEnvironment) : this(
        hosts = environment.config.property("$PATH.hosts").getString(),
        port = environment.config.property("$PATH.port").getString().toInt(),
        user = environment.config.property("$PATH.user").getString(),
        pass = environment.config.property("$PATH.pass").getString(),
        keyspace = environment.config.property("$PATH.keyspace").getString()
    )

    companion object {
        const val PATH = "ktor.repository.cassandra"
    }
}