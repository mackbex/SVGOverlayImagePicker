package com.picker.overlay.util

/**
 * Retrofit Response용 mapper
 */
interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity:Entity):DomainModel
    fun mapToEntity(model:DomainModel):Entity
}