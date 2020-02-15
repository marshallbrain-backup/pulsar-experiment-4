package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.Constructable
import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.colony.production.jobs.Workable
import com.marshalldbrain.pulsar.resources.Resource
import com.marshalldbrain.pulsar.resources.ResourceCollection
import com.marshalldbrain.pulsar.resources.times

data class District (
	var type: DistrictType = DistrictType.emptyDistrict,
	var amount: Int = 0
) : Constructable, ResourceCollection, Workable {
	
	override val time: Int
		get() = type.time
	override val cost: Set<Resource>
		get() = type.cost
	override val id: String
		get() = type.id
	override val income: Set<Resource>
		get() {
			return if(amount == 0) {
				emptySet()
			} else {
				(type.production + type.upkeep * -1) * amount
			}
		}
	override val jobGroups = type.jobs.associateBy { it.jobType }
	
	fun build(amount: Int = 1): ConstructionTask {
		
		return ConstructionTask(
			id,
			ConstructionType.BUILD,
			time,
			amount,
			cost,
			onComplete = { this.amount++ }
		)
		
	}
	
	override fun createTask(type: ConstructionType, amount: Int): ConstructionTask {
		
		return when(type) {
			ConstructionType.BUILD -> build(amount)
		}
		
	}
	
}
