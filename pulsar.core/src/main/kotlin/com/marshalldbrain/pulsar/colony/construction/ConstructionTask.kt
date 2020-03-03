package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.ResourceType
import kotlin.math.absoluteValue

class ConstructionTask(
	val type: String,
	val cost: Map<ResourceType, Int>,
	val time: Int,
	val amount: Int,
	private val onComplete: () -> Unit = {}
) {
	
	//"Project"
	// "Amount\nRemaining"
	// " % of \n Capacity "
	// "Production\nRate"
	// "Time\nRemaining"
	// "Estimated Completion\nDate"
	
	var timeRemaining: Int = time
		private set
	var amountRemaining: Int = amount
		private set
	
	val projectName: String
		get() = type
	
	fun passTime(timePass: Int): Int {
		
		if (time == 0) {
			repeat(amount) {
				runOnComplete()
			}
			amountRemaining = 0
			return 0
		}
		
		var remaining = timePass
		do {
			remaining -= timeRemaining
			if (remaining >= 0) {
				amountRemaining--
				timeRemaining = time
				runOnComplete()
			}
		} while (remaining > 0 && amountRemaining > 0)
		
		if (amountRemaining <= 0) {
			return remaining
		}
		
		timeRemaining = remaining.absoluteValue
		return 0
		
	}
	
	fun runOnComplete() {
		onComplete.invoke()
	}
	
}