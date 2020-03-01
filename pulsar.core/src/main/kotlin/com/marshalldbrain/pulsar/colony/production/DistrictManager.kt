package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.resources.ResourceUpdater

class DistrictManager(
	private val allDistrictTypes: Set<DistrictType>,
	private val resourceUpdater: ResourceUpdater,
	private val jobUpdater: JobUpdater
) : DistrictInfo {
	
	override val max = 5
	
	private val mutableDistrictMap = possibleTypes
		.filter { it.starting }
		.take(max)
		.associateWith { 0 }
		.toMutableMap()
	
	override val remaining: Int
		get() = max - districts.size
	override val possibleTypes: Set<DistrictType>
		get() = allDistrictTypes.filter { it.possible }.toSet()
	override val remainingTypes: Set<DistrictType>
		get() = allDistrictTypes - districts.keys
	override val districts: Map<DistrictType, Int>
		get() = mutableDistrictMap
	
	override fun createConstructionTask(target: DistrictType, type: ConstructionType, amount: Int): ConstructionTask {
		
		if (!districts.containsKey(target)) {
			throw NoSuchElementException()
		}
		
		val onComplete = when(type) {
			ConstructionType.BUILD -> {{
				
				mutableDistrictMap[target] = mutableDistrictMap.getValue(target) + 1
				
				resourceUpdater.update(target.production)
				resourceUpdater.update(target.upkeep, -1)
				jobUpdater.update(target.jobs)
				
			}}
		}
		
		return ConstructionTask(type, target, amount, onComplete)
		
	}
	
}

interface DistrictInfo {
	
	val max: Int
	val remaining: Int
	val districts: Map<DistrictType, Int>
	val possibleTypes: Set<DistrictType>
	val remainingTypes: Set<DistrictType>
	
	fun createConstructionTask(target: DistrictType, type: ConstructionType, amount: Int): ConstructionTask
	
}