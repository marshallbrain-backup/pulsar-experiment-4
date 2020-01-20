package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.Resource
import kotlin.math.absoluteValue

class ConstructionTask(
	time: Int,
	val cost: List<Resource>,
	private val onStart: () -> Unit,
	private val onCancel: () -> Unit,
	private val onFinnish: () -> Unit,
	private val whileConstruction: () -> Unit
) {
	
	var timeRemaining: Int = time
	private set
	
	fun passTime(amount: Int): Int {
		timeRemaining -= amount
		if (timeRemaining <= 0) {
			return timeRemaining.absoluteValue
		}
		return 0
	}
	
	fun onStart() {
		onStart.invoke()
	}
	
	fun onCancel() {
		onCancel.invoke()
	}
	
	fun onFinnish() {
		onFinnish.invoke()
	}
	
	fun whileConstructing() {
		whileConstruction.invoke()
	}

}