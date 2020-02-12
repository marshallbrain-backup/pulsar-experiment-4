package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.Resource

interface Constructable {
	
	val id: String
	val time: Int
	val cost: Set<Resource>
	
	fun createTask(type: ConstructionType, amount: Int = 0): ConstructionTask

}