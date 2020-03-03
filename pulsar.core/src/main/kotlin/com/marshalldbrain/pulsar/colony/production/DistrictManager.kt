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
	
	override fun createConstructionTask(
		target: Pair<DistrictType, DistrictType>,
		type: ConstructionType,
		amount: Int
	): ConstructionTask {
		
		if (!districts.containsKey(target.first)) {
			throw NoSuchElementException()
		}
		
		return when (type) {
			
			ConstructionType.BUILD -> {
				ConstructionTask(
					"Building ${target.first}",
					target.first.cost, target.first.time, amount
				) { OnComplete().build(target) }
			}
			
			ConstructionType.DESTROY -> {
				ConstructionTask(
					"Destroying ${target.first}",
					emptyMap(), 0, amount
				) { OnComplete().destroy(target) }
			}
			
			ConstructionType.REPLACE -> {
				ConstructionTask(
					"Replacing ${target.first} with ${target.second}",
					target.second.cost, target.second.time, amount
				) { OnComplete().replace(target) }
			}
			
			ConstructionType.TOOL -> {
				ConstructionTask(
					"Tooling ${target.first} to ${target.second}",
					target.second.cost, target.second.time, amount
				) { OnComplete().tool(target) }
			}
			
			ConstructionType.DETOOL -> {
				ConstructionTask(
					"Detooling ${target.first}",
					emptyMap(), 0, amount
				) { OnComplete().detool(target) }
			}
			
			else -> throw Exception("Unsupported Construction Type: $type")
		}
		
	}
	
	private inner class OnComplete {
		
		fun build(target: Pair<DistrictType, DistrictType>) {
			
			mutableDistrictMap[target.first] = mutableDistrictMap.getValue(target.first) + 1
			
			resourceUpdater.update(target.first.production)
			resourceUpdater.update(target.first.upkeep, -1)
			jobUpdater.update(target.first.jobs)
			
		}
		
		fun destroy(target: Pair<DistrictType, DistrictType>) {
			
			if (mutableDistrictMap[target.first]!! > 0) {
				
				mutableDistrictMap[target.first] = mutableDistrictMap.getValue(target.first) - 1
				
				resourceUpdater.update(target.first.production, -1)
				resourceUpdater.update(target.first.upkeep)
				jobUpdater.update(target.first.jobs, -1)
				
			}
			
		}
		
		fun replace(target: Pair<DistrictType, DistrictType>) {
			
			if (mutableDistrictMap[target.first]!! > 0) {
				
				mutableDistrictMap[target.first] = mutableDistrictMap.getValue(target.first) - 1
				
				resourceUpdater.update(target.first.production, -1)
				resourceUpdater.update(target.first.upkeep)
				jobUpdater.update(target.first.jobs, -1)
				
				mutableDistrictMap[target.second] = mutableDistrictMap.getValue(target.second) + 1
				
				resourceUpdater.update(target.second.production)
				resourceUpdater.update(target.second.upkeep, -1)
				jobUpdater.update(target.second.jobs)
				
			}
			
		}
		
		fun tool(target: Pair<DistrictType, DistrictType>) {
			
			if (mutableDistrictMap.containsKey(target.first)) {
				
				val amount = mutableDistrictMap[target.first] ?: 0
				
				resourceUpdater.update(target.second.production, amount)
				resourceUpdater.update(target.second.upkeep, -amount)
				jobUpdater.update(target.second.jobs, amount)
				
				mutableDistrictMap[target.second] = amount
				
				resourceUpdater.update(target.first.production, -amount)
				resourceUpdater.update(target.first.upkeep, amount)
				jobUpdater.update(target.first.jobs, -amount)
				
				mutableDistrictMap.remove(target.first)
				
			}
			
		}
		
		fun detool(target: Pair<DistrictType, DistrictType>) {
			
			if (mutableDistrictMap.containsKey(target.first)) {
				
				val amount = mutableDistrictMap[target.first] ?: 0
				
				resourceUpdater.update(target.first.production, -amount)
				resourceUpdater.update(target.first.upkeep, amount)
				jobUpdater.update(target.first.jobs, -amount)
				
				mutableDistrictMap.remove(target.first)
				
			}
			
		}
	
	}
	
}

interface DistrictInfo {
	
	val max: Int
	val remaining: Int
	val districts: Map<DistrictType, Int>
	val possibleTypes: Set<DistrictType>
	val remainingTypes: Set<DistrictType>
	
	fun createConstructionTask(
		target: Pair<DistrictType, DistrictType>,
		type: ConstructionType, amount: Int
	): ConstructionTask
	
}