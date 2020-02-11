package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.resources.Resource

data class DistrictType (
	val id: String,
	val time: Int = 0,
	val possible: Boolean = true,
	val starting: Boolean = false,
	val cost: Set<Resource> = emptySet(),
	val production: Set<Resource> = emptySet(),
	val upkeep: Set<Resource> = emptySet()
) {
	
	val isTooled: Boolean
		get() = this != emptyDistrict
	
	companion object {
		val emptyDistrict = DistrictType("")
	}
	
}