package com.marshalldbrain.pulsar.colony.production.jobs

import com.marshalldbrain.pulsar.resources.Resource

data class JobType(
	val id: String,
	val production: Set<Resource> = emptySet(),
	val upkeep: Set<Resource> = emptySet()
) {
}