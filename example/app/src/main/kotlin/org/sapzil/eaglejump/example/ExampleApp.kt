package org.sapzil.eaglejump.example

import com.google.inject.Guice
import com.google.protobuf.util.JsonFormat
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.MediaType
import com.linecorp.armeria.common.SessionProtocol
import com.linecorp.armeria.server.ServerBuilder
import org.h2.jdbcx.JdbcDataSource
import org.hibernate.dialect.H2Dialect
import org.hibernate.jpa.HibernatePersistenceProvider
import org.sapzil.eaglejump.db.DefaultPersistenceUnitInfo
import org.sapzil.eaglejump.db.EntityManagerHolder
import org.sapzil.eaglejump.example.handler.GetDocumentHandler
import org.sapzil.eaglejump.example.model.Document
import org.sapzil.eaglejump.example.protocol.GetDocumentRequest
import reactor.core.publisher.toMono
import java.time.Instant
import java.util.*

fun main() {
    val start = System.currentTimeMillis()

    val pui = DefaultPersistenceUnitInfo(
        persistenceUnitName = "ej",
        managedClassNames = listOf(Document::class.java.name),
        properties = Properties().apply {
            put("hibernate.dialect", H2Dialect::class.java.name)
            put("hibernate.show_sql", true)
            put("javax.persistence.schema-generation.database.action", "create")
        },
        dataSource = JdbcDataSource().apply { setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1") }
    )
    val pp = HibernatePersistenceProvider()
    val emf = pp.createContainerEntityManagerFactory(pui, Properties())
//    Document.find(1)
//    Document.query { it[Document::slug].equal("hello-world")
//        .and(it[Document::title].notEqual(it[Document::slug])) }.resultList

    val end = System.currentTimeMillis()

    println("Hibernate: ${end - start} ms")

    EntityManagerHolder.entityManager = emf.createEntityManager()
    val tx = EntityManagerHolder.get().transaction
    tx.begin()
    Document.create(Document(
        id = 1,
        title = "Hello",
        slug = "hello",
        body = "world",
        updatedAt = Instant.now()
    ))
    tx.commit()

    val injector = Guice.createInjector(
        ExampleModule()
    )

    val handler = injector.getInstance(GetDocumentHandler::class.java)

    val jsonParser = JsonFormat.parser().ignoringUnknownFields()
    val jsonPrinter = JsonFormat.printer()

    val sb = ServerBuilder()
    sb.service("/") { ctx, res ->
        HttpResponse.from(ctx.request().aggregate()!!.toMono()
            .map {
                val builder = GetDocumentRequest.newBuilder()
                jsonParser.merge(it.contentUtf8(), builder)
                builder.build()
            }
            .flatMap {
                val tx = EntityManagerHolder.get().transaction
                tx.begin()
                val r = handler.invoke(it).toMono()
                tx.commit()
                r
            }
            .map { HttpResponse.of(MediaType.JSON_UTF_8, jsonPrinter.print(it)) }
            .doOnError { it.printStackTrace() }
            .toFuture()
        )
    }
    sb.port(8080, SessionProtocol.HTTP)
    val server = sb.build()
    server.start().join()
}