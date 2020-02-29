package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.resources.ResourceType

data class JobType(
	val id: String,
	val production: Map<ResourceType, Int> = emptyMap(),
	val upkeep: Map<ResourceType, Int> = emptyMap()
)