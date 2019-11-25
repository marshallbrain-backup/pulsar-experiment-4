package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.colony.districts.DistrictType

data class Colony(val districtTypes: Map<String, DistrictType>) {
	
	val districts = MutableList(5) {
		District(DistrictType(""))
	}
	
	init {
		
		val startingDistrictTypes = districtTypes.values.filter { it.possible() }.sortedBy { it.id }
		for (i in min(districts.indices, startingDistrictTypes.indices)) {
			districts[i].type = startingDistrictTypes[i]
		}
		
	}
	
	private fun min(a: IntRange, b: IntRange): IntRange {
		return if (a.last > b.last) b else a
	}
	
}
