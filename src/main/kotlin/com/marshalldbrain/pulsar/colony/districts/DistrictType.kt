package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.resources.Resource

data class DistrictType (
	val id: String,
	val cost: List<Resource>,
	val production: List<Resource>,
	val upkeep: List<Resource>
) {
}