package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.collections.queueOf

class ConstructionManager {
	
	val constructionQueue = queueOf<ConstructionTask>()
	
	var currentTask: ConstructionTask? = null
	
	fun addToQueue(task: ConstructionTask) {
		
		if (task.amountRemaining <= 0) {
			return
		}
		if (task.target.time == 0) {
			task.passTime(0)
		}
		if (currentTask == null) {
			currentTask = task
			return
		}
		
		constructionQueue.add(task)
		
	}
	
	fun tick(amount: Int) {
		
		var remaining = amount
		
		while (true) {
			val task = currentTask
			if (task == null) {
				return
			} else {
				
				remaining = task.passTime(remaining)
				if (remaining <= 0) {
					return
				}
				
				if (task.amountRemaining > 0) {
					throw Exception("Invalid state, task has positive amount remaining")
				}
				
				currentTask = constructionQueue.poll()
				
			}
		}
		
	}
	
}