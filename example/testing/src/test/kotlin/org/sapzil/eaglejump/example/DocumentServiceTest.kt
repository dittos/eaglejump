package org.sapzil.eaglejump.example

import com.google.inject.Guice
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.sapzil.eaglejump.example.protocol.GetDocumentRequest
import javax.inject.Inject

@RunWith(JUnit4::class)
class DocumentServiceTest {
    @Inject
    lateinit var documentService: DocumentService.BlockingClient

    @Before
    fun setUp() {
        val injector = Guice.createInjector(ExampleModule())
        injector.injectMembers(this)
    }

    @Test
    @Throws(Exception::class)
    fun getDocument_notExisting() {
        documentService.getDocument(GetDocumentRequest.newBuilder()
            .setSlug("hi")
            .build())
    }
}