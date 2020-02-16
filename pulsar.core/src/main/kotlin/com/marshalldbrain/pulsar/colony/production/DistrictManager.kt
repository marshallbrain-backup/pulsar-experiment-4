package com.marshalldbrain.pulsar.colony.production

class DistrictManager(private val allDistrictTypes: Set<DistrictType>) {
	
	private val mutableDistrictMap = districtTypes.filter { it.possible }.associateWith { 0 }.toMutableMap()
	private val max = 5
	
	val districtTypes: Set<DistrictType>
		get() {
			return allDistrictTypes.filter { it.possible }.toSet()
		}
	val districts: Map<DistrictType, Int>
		get() = mutableDistrictMap.toMap()
	
}