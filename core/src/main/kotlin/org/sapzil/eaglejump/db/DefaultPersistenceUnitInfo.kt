package org.sapzil.eaglejump.db

import java.net.URL
import java.util.*
import javax.persistence.SharedCacheMode
import javax.persistence.ValidationMode
import javax.persistence.spi.ClassTransformer
import javax.persistence.spi.PersistenceUnitInfo
import javax.persistence.spi.PersistenceUnitTransactionType
import javax.sql.DataSource

class DefaultPersistenceUnitInfo(
    private val persistenceUnitName: String,
    private val managedClassNames: List<String>,
    private val properties: Properties,
    private val dataSource: DataSource
) : PersistenceUnitInfo {
    companion object {
        const val JPA_VERSION = "2.1"
    }

    override fun getPersistenceUnitRootUrl(): URL? {
        return null
    }

    override fun getJtaDataSource(): DataSource? {
        return null
    }

    override fun getMappingFileNames(): List<String> {
        return emptyList()
    }

    override fun getNewTempClassLoader(): ClassLoader? {
        return null
    }

    override fun getPersistenceUnitName(): String {
        return persistenceUnitName
    }

    override fun getSharedCacheMode(): SharedCacheMode {
        return SharedCacheMode.UNSPECIFIED
    }

    override fun getClassLoader(): ClassLoader {
        return Thread.currentThread().contextClassLoader
    }

    override fun getTransactionType(): PersistenceUnitTransactionType {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL
    }

    override fun getProperties(): Properties {
        return properties
    }

    override fun getPersistenceXMLSchemaVersion(): String {
        return JPA_VERSION
    }

    override fun addTransformer(transformer: ClassTransformer?) {
    }

    override fun getManagedClassNames(): List<String> {
        return managedClassNames
    }

    override fun getJarFileUrls(): List<URL> {
        return emptyList()
    }

    override fun getPersistenceProviderClassName(): String {
        return javaClass.name
    }

    override fun getNonJtaDataSource(): DataSource {
        return dataSource
    }

    override fun excludeUnlistedClasses(): Boolean {
        return false
    }

    override fun getValidationMode(): ValidationMode {
        return ValidationMode.AUTO
    }
}