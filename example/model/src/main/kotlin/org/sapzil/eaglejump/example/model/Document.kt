package org.sapzil.eaglejump.example.model

import org.sapzil.eaglejump.db.EntityMeta
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Document(
    @Id
    val id: Long,

    val title: String,

    val slug: String,

    val body: String,

    val updatedAt: Instant
) {
    companion object : EntityMeta<Document, Long>()
}