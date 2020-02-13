package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.colony.districts.DistrictType
import com.marshalldbrain.pulsar.resources.ResourceBucket
import com.marshalldbrain.pulsar.resources.ResourceMaster

class Colony(private val allDistrictTypes: Set<DistrictType>, teller: ResourceMaster) {
	
	val districts = MutableList(5) {
		District()
	}
	val constructionManager = ConstructionManager(teller)
	val resourceBucket = ResourceBucket(districts)
	
	init {
		
		val startingDistrictTypes = districtTypes.filter { it.starting }.sortedBy { it.id }
		for (i in min(districts.indices, startingDistrictTypes.indices)) {
			districts[i].type = startingDistrictTypes[i]
		}
		
		teller.sourceList.add(resourceBucket)
		
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
