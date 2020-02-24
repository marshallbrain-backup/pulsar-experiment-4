package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.Constructable
import com.marshalldbrain.pulsar.resources.Resource

data class DistrictType (
	override val id: String,
	override val time: Int = 0,
	val possible: Boolean = true,
	val starting: Boolean = false,
	override val cost: List<Resource> = emptyList(),
	val production: List<Resource> = emptyList(),
	val upkeep: List<Resource> = emptyList()
) : Constructable {
	
	val isTooled: Boolean
		get() = this != emptyDistrict
	
	companion object {
		val emptyDistrict = DistrictType("")
	}
	
}