package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.ion.collections.getOrDefault
import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.resources.ResourceUpdater

class DistrictManager(
	private val allDistrictTypes: Set<DistrictType>,
	private val resource: ResourceUpdater,
	private val job: JobUpdater
) : DistrictInfo {
	
	override val max = 5
	
	private val map = possibleTypes
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
		get() = map
	
	override fun createConstructionTask(
		target: List<DistrictType>,
		type: ConstructionType,
		amount: Int
	): ConstructionTask {
		
		return when (type) {
			
			ConstructionType.BUILD -> {
				ConstructionTask(
					"Building ${target[0].id}",
					target[0].cost, target[0].time, amount
				) { OnComplete().build(target) }
			}
			
			ConstructionType.DESTROY -> {
				ConstructionTask(
					"Destroying ${target[0].id}",
					emptyMap(), 0, amount
				) { OnComplete().destroy(target) }
			}
			
			ConstructionType.REPLACE -> {
				ConstructionTask(
					"Replacing ${target[1].id} with ${target[0].id}",
					target[0].cost, target[0].time, amount
				) { OnComplete().replace(target) }
			}
			
			ConstructionType.TOOL -> {
				ConstructionTask(
					"Tooling ${if(target.size > 1) target[1].id else "untooled"} to ${target[0].id}",
					target[0].cost, target[0].time, amount
				) { OnComplete().tool(target) }
			}
			
			ConstructionType.DETOOL -> {
				ConstructionTask(
					"Detooling ${target[0].id}",
					emptyMap(), 0, amount
				) { OnComplete().detool(target) }
			}
			
			else -> throw Exception("Unsupported Construction Type: $type")
		}
		
	}
	
	private fun modifyDistrict(target: DistrictType, amount: Int) {
		
		map[target] = map.getValue(target) + amount
		
		resource.update(target.production, amount)
		resource.update(target.upkeep, -amount)
		job.update(target.jobs, amount)
		
	}
	
	private inner class OnComplete {
		
		fun build(target: List<DistrictType>) {
			modifyDistrict(target[0], 1)
		}
		
		fun destroy(target: List<DistrictType>) {
			if (map[target[0]]!! > 0) {
				modifyDistrict(target[0], -1)
			}
		}
		
		fun replace(target: List<DistrictType>) {
			if (map[target[1]]!! > 0) {
				modifyDistrict(target[1], -1)
				modifyDistrict(target[0], 1)
			}
		}
		
		fun tool(target: List<DistrictType>) {
			if (target.size > 1 && map.containsKey(target[1])) {
				modifyDistrict(target[1], map.getValue(target[1]))
				map.remove(target[1])
			}
			map[target[0]] = 0
		}
		
		fun detool(target: List<DistrictType>) {
			if (map.containsKey(target[0])) {
				modifyDistrict(target[0], map.getValue(target[0]))
				map.remove(target[0])
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
		target: List<DistrictType>,
		type: ConstructionType, amount: Int
	): ConstructionTask
	
}