package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.Constructible
import com.marshalldbrain.pulsar.resources.ResourceType

data class DistrictType (
	override val id: String,
	override val time: Int = 0,
	val possible: Boolean = true,
	val starting: Boolean = false,
	override val cost: Map<ResourceType, Int> = emptyMap(),
	val production: Map<ResourceType, Int> = emptyMap(),
	val upkeep: Map<ResourceType, Int> = emptyMap(),
	val jobs: Map<JobType, Int> = emptyMap()
) : Constructible {
	
	val isTooled: Boolean
		get() = this != emptyDistrict
	
	companion object {
		val emptyDistrict = DistrictType("Untooled")
	}
	
}