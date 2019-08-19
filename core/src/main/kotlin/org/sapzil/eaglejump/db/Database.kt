package org.sapzil.eaglejump.db

import javax.persistence.criteria.*
import kotlin.reflect.KProperty1

class KCriteriaBuilder(private val cb: CriteriaBuilder) {
    // Ordering

    fun Expression<*>.desc(): Order = cb.desc(this)

    fun Expression<*>.asc(): Order = cb.asc(this)

    // Aggregate functions

    fun <N : Number> Expression<N>.avg(): Expression<Double> = cb.avg(this)

    fun <N : Number> Expression<N>.sum(): Expression<N> = cb.sum(this)

    fun Expression<Int>.sumAsLong(): Expression<Long> = cb.sumAsLong(this)

    fun Expression<Float>.sumAsDouble(): Expression<Double> = cb.sumAsDouble(this)

    fun <N : Number> Expression<N>.max(): Expression<N> = cb.max(this)

    fun <N : Number> Expression<N>.min(): Expression<N> = cb.min(this)

    fun <X : Comparable<X>> Expression<X>.greatest(): Expression<X> = cb.greatest(this)

    fun <X : Comparable<X>> Expression<X>.least(): Expression<X> = cb.least(this)

    fun Expression<*>.count(): Expression<Long> = cb.count(this)

    fun Expression<*>.countDistinct(): Expression<Long> = cb.countDistinct(this)

    // Subqueries

    // Boolean functions

    fun Expression<Boolean>.and(y: Expression<Boolean>): Predicate = cb.and(this, y)

    fun Predicate.and(vararg restrictions: Predicate): Predicate = cb.and(this, *restrictions)

    fun Expression<Boolean>.or(y: Expression<Boolean>): Predicate = cb.or(this, y)

    fun Predicate.or(vararg restrictions: Predicate): Predicate = cb.or(this, *restrictions)

    fun Expression<Boolean>.not(): Predicate = cb.not(this)

    fun conjunction(): Predicate = cb.conjunction()

    fun disjunction(): Predicate = cb.disjunction()

    fun Expression<Boolean>.isTrue(): Predicate = cb.isTrue(this)

    fun Expression<Boolean>.isFalse(): Predicate = cb.isFalse(this)

    fun Expression<*>.equal(y: Expression<*>): Predicate = cb.equal(this, y)

    fun Expression<*>.equal(y: Any): Predicate = cb.equal(this, y)

    fun Expression<*>.notEqual(y: Expression<*>): Predicate = cb.notEqual(this, y)

    fun Expression<*>.notEqual(y: Any): Predicate = cb.notEqual(this, y)

    operator fun <X, Y> Path<X>.get(property: KProperty1<X, Y>): Path<Y> =
        get<Y>(property.name)
}