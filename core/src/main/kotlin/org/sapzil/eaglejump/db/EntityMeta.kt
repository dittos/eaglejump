package org.sapzil.eaglejump.db

import javax.persistence.TypedQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import kotlin.reflect.KClass

abstract class EntityMeta<T : Any, ID : Any> {
    private val entityClass: Class<T> by lazy { this::class.supertypes.first { it.classifier == EntityMeta::class }
        .arguments[0].type!!.classifier!!.let { it as KClass<T> }.java }

    fun find(id: ID): T? {
        return EntityManagerHolder.get().find(entityClass, id)
    }

    fun create(entity: T) {
        EntityManagerHolder.get().persist(entity)
    }

    fun query(buildPredicate: KCriteriaBuilder.(Root<T>) -> Predicate): TypedQuery<T> {
        val em = EntityManagerHolder.get()
        val rawCb = em.criteriaBuilder
        val cb = KCriteriaBuilder(rawCb)
        val query = rawCb.createQuery(entityClass)
        val root = query.from(entityClass)
        query.where(cb.buildPredicate(root))
        return em.createQuery(query)
    }
}