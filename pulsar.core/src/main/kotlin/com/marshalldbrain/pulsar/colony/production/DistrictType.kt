package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.resources.ResourceType

data class DistrictType (
	val id: String,
	val time: Int = 0,
	val possible: Boolean = true,
	val starting: Boolean = false,
	val cost: Map<ResourceType, Int> = emptyMap(),
	val production: Map<ResourceType, Int> = emptyMap(),
	val upkeep: Map<ResourceType, Int> = emptyMap(),
	val jobs: Map<JobType, Int> = emptyMap()
)