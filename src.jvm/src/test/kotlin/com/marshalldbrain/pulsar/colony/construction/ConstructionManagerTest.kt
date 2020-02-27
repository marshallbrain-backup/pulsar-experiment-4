package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.sequences.randomIntRange
import com.marshalldbrain.pulsar.resources.Resource
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import kotlin.random.Random

class ConstructionManagerTest : FunSpec({
	
	context("Process time") {
		
		test("Equal time passed") {
		
			val cm = ConstructionManager()
			val task = ConstructionTask(ConstructionType.BUILD, Task("task", 5, emptyList()), 1)
			
			cm.addToQueue(task)
			cm.tick(5)
			
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		test("0 time instantly finishes") {
			
			val cm = ConstructionManager()
			val task = ConstructionTask(ConstructionType.BUILD, Task("task", 0, emptyList()), 1)
			
			cm.addToQueue(task)
			
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		test("Task with multiple amount") {
			
			val cm = ConstructionManager()
			val task = ConstructionTask(ConstructionType.BUILD, Task("task1", 5, emptyList()), 4)
			
			cm.addToQueue(task)
			cm.tick(18)
			
			cm.constructionQueue.shouldBeEmpty()
			cm.currentTask.shouldNotBeNull()
			cm.currentTask!!.amountRemaining shouldBe 1
			cm.currentTask!!.timeRemaining shouldBe 2
			
		}
		
		test("Time rolled over") {
			
			val cm = ConstructionManager()
			val tasks = listOf(
				ConstructionTask(ConstructionType.BUILD, Task("task1", 5, emptyList()), 1),
				ConstructionTask(ConstructionType.BUILD, Task("task2", 5, emptyList()), 1)
			)
			
			tasks.forEach { cm.addToQueue(it) }
			cm.tick(7)
			
			cm.constructionQueue.shouldBeEmpty()
			cm.currentTask.shouldNotBeNull()
			cm.currentTask!!.timeRemaining shouldBe 3
			
		}
		
	}
	
	test("OnComplete triggers") {
		
		var complete = false
		
		val cm = ConstructionManager()
		val task = ConstructionTask(ConstructionType.BUILD, Task("Task",5, listOf()), 1)
		{ complete = true }
		
		cm.addToQueue(task)
		cm.tick(5)
		
		cm.constructionQueue.shouldBeEmpty()
		complete shouldBe true
		
	}
	
	test("OnComplete triggers multiple times") {
		
		var number = 0
		
		val cm = ConstructionManager()
		val task = ConstructionTask(ConstructionType.BUILD, Task("Task",5, listOf()), 4)
		{ number++ }
		
		cm.addToQueue(task)
		cm.tick(20)
		
		cm.constructionQueue.shouldBeEmpty()
		number shouldBe 4
		
	}
	
})

private class Task (
	override val id: String,
	override val time: Int,
	override val cost: List<Resource>
) : Constructible