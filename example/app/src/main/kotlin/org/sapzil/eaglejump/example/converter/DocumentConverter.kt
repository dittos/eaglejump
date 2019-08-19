package org.sapzil.eaglejump.example.converter

import com.google.protobuf.Timestamp
import org.sapzil.eaglejump.example.model.Document
import org.sapzil.eaglejump.example.protocol.DocumentDTO

class DocumentConverter {
    fun toProto(model: Document): DocumentDTO {
        return DocumentDTO.newBuilder()
            .setId(model.id.toString())
            .setTitle(model.title)
            .setSlug(model.slug)
            .setBody(model.body)
            .setUpdatedAt(Timestamp.newBuilder().setSeconds(model.updatedAt.epochSecond).setNanos(model.updatedAt.nano))
            .build()
    }
}