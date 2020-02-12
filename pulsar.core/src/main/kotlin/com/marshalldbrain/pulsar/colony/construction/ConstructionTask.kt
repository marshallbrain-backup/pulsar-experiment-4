package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.resources.Resource
import kotlin.math.absoluteValue

class ConstructionTask (
	val id: String,
	val type: Type,
	val time: Int,
	val amount: Int,
	val totalCost: Set<Resource>,
	val onComplete: () -> Unit
) {
	
	//TODO move creation to the Constructable
	//TODO add onComplete value
	
	var timeRemaining: Int = amount * time
		private set
	val amountRemaining: Int
		get() = timeRemaining / time + 1
	
	val projectName: String
		get() = "$type $id"
	
	fun passTime(timePass: Int): Int {
		val remaining = timeRemaining - timePass
		if (remaining <= 0) {
			timeRemaining = 0
			return remaining.absoluteValue
		}
		timeRemaining = remaining
		return -1
	}
	
	enum class Type {
		
		BUILD {
			override fun toString(): String {
				return "Building"
			}
		}
		
	}
	
}
