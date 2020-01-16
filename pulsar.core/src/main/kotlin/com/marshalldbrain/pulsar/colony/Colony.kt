package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.colony.districts.DistrictType

data class Colony(private val allDistrictTypes: Set<DistrictType>) {
	
	val districts = MutableList(5) {
		District()
	}
	
	init {
		
		val startingDistrictTypes = districtTypes.filter { it.starting }.sortedBy { it.id }
		for (i in min(districts.indices, startingDistrictTypes.indices)) {
			districts[i].type = startingDistrictTypes[i]
		}
		
	}
	
	private fun min(a: IntRange, b: IntRange): IntRange {
		return if (a.last > b.last) b else a
	}
	
	val untooledDistricts: List<District>
		get() {
			return districts.filter { it.type == DistrictType.emptyDistrict }
		}
	
	val districtTypes: Set<DistrictType>
		get() {
			return allDistrictTypes.filter { type ->
				type.possible and !districts.any { it.type == type }
			}.toSet()
		}
	
}
