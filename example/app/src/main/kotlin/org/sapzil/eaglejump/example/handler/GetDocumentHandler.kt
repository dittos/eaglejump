package org.sapzil.eaglejump.example.handler

import org.reactivestreams.Publisher
import org.sapzil.eaglejump.example.DocumentService
import org.sapzil.eaglejump.example.converter.DocumentConverter
import org.sapzil.eaglejump.example.operations.DocumentOperations
import org.sapzil.eaglejump.example.protocol.DocumentDTO
import org.sapzil.eaglejump.example.protocol.GetDocumentRequest
import reactor.core.publisher.Mono
import javax.inject.Inject

class GetDocumentHandler @Inject constructor(
    private val documentOperations: DocumentOperations,
    private val documentConverter: DocumentConverter
) : DocumentService.GetDocument {
    override fun invoke(request: GetDocumentRequest): Publisher<DocumentDTO> {
        val document = documentOperations.getBySlug(request.slug) ?:
            return Mono.error(Exception("not found"))
        return Mono.just(documentConverter.toProto(document))
    }
}