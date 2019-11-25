package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.resources.Resource

data class DistrictType (
	val id: String,
	val cost: List<Resource> = emptyList(),
	val production: List<Resource> = emptyList(),
	val upkeep: List<Resource> = emptyList()
) {
}