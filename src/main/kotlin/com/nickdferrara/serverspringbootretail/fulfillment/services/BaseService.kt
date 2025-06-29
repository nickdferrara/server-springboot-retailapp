package com.nickdferrara.serverspringbootretail.fulfillment.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

abstract class BaseService<T : Any, ID : Any>(
    protected val repository: JpaRepository<T, ID>
) {

    fun save(entity: T): T = repository.save(entity)

    fun saveAll(entities: Iterable<T>): List<T> = repository.saveAll(entities)

    fun findById(id: ID): Optional<T> = repository.findById(id)

    fun existsById(id: ID): Boolean = repository.existsById(id)

    fun findAll(): List<T> = repository.findAll()

    fun findAll(sort: Sort): List<T> = repository.findAll(sort)

    fun findAll(pageable: Pageable): Page<T> = repository.findAll(pageable)

    fun findAllById(ids: Iterable<ID>): List<T> = repository.findAllById(ids)

    fun count(): Long = repository.count()

    fun delete(entity: T) = repository.delete(entity)

    fun deleteAll(entities: Iterable<T>) = repository.deleteAll(entities)

    fun deleteById(id: ID) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()

    fun flush() = repository.flush()

    fun saveAndFlush(entity: T): T = repository.saveAndFlush(entity)

    fun deleteInBatch(entities: Iterable<T>) = repository.deleteInBatch(entities)

    fun deleteAllInBatch() = repository.deleteAllInBatch()
}