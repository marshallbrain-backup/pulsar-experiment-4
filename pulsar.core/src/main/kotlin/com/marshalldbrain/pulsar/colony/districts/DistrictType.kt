package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.resources.Resource

data class DistrictType (
	val id: String,
	val time: Int = 0,
	val cost: List<Resource> = emptyList(),
	val production: List<Resource> = emptyList(),
	val upkeep: List<Resource> = emptyList()
) {
	
	fun possible(): Boolean {
		return true
	}
	
	fun starting(): Boolean {
		return true
	}
	
}