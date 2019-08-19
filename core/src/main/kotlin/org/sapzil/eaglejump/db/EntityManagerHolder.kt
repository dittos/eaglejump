package org.sapzil.eaglejump.db

import javax.persistence.EntityManager

object EntityManagerHolder {
    var entityManager: EntityManager? = null

    fun get(): EntityManager = entityManager!!
}