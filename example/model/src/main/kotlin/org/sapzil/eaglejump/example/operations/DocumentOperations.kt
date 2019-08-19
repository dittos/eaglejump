package org.sapzil.eaglejump.example.operations

import org.sapzil.eaglejump.example.model.Document

class DocumentOperations {
    fun getBySlug(slug: String): Document? {
        return Document.query { it[Document::slug].equal(slug) }
            .resultStream.findFirst().orElse(null)
    }
}