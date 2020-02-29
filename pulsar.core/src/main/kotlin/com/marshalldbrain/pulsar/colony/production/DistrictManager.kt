package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.resources.ResourceUpdater

class DistrictManager(
	private val allDistrictTypes: Set<DistrictType>,
	private val resourceUpdater: ResourceUpdater,
) {
	
	private val mutableDistrictMap = districtTypes.filter { it.starting }.associateWith { 0 }.toMutableMap()
	private val max = 5
	
	val districtTypes: Set<DistrictType>
		get() {
			return allDistrictTypes.filter { it.possible }.toSet()
		}
	val districts: Map<DistrictType, Int>
		get() = mutableDistrictMap.toMap()
	
	fun createConstructionTask(target: DistrictType, type: ConstructionType, amount: Int): ConstructionTask {
		
		if (!districts.containsKey(target)) {
			throw NoSuchElementException()
		}
		
		val onComplete = when(type) {
			ConstructionType.BUILD -> {{
				
				mutableDistrictMap[target] = mutableDistrictMap.getValue(target) + 1
				
				resourceUpdater.update(target.production)
				resourceUpdater.update(target.upkeep, -1)
				
			}}
		}
		
		return ConstructionTask(type, target, amount, onComplete)
		
	}
	
}