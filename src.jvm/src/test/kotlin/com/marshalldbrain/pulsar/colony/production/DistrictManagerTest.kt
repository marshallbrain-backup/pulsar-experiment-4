package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceType
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe
import io.mockk.mockk

class DistrictManagerTest : FunSpec({
	
	test("Possible district types are correct") {
		
		val districts = setOf(
			DistrictType("1", possible = false),
			DistrictType("2"),
			DistrictType("3"),
			DistrictType("4", possible = false)
		)
		
		val expected = setOf(
			DistrictType("2"),
			DistrictType("3")
		)
		
		val dm = DistrictManager(districts, mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
		
		dm.districtTypes.shouldContainExactly(expected)
	
	}
	
	test("Starting district types are correct") {
		
		val districts = setOf(
			DistrictType("1", starting = true),
			DistrictType("2"),
			DistrictType("3", starting = true),
			DistrictType("4")
		)
		
		val expected = setOf(
			DistrictType("1", starting = true),
			DistrictType("3", starting = true)
		)
		
		val dm = DistrictManager(districts, mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
		
		dm.districts.keys.shouldContainExactly(expected)
		
	}
	
	context("Create task") {
		
		test("Multiple amount") {
			
			val type = DistrictType("1", 5, starting = true)
			
			val dm = DistrictManager(setOf(type), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
			val cm = ConstructionManager(mockk(relaxUnitFun = true))
			
			val task = dm.createConstructionTask(type, ConstructionType.BUILD, 4)
			cm.addToQueue(task)
			cm.tick(20)
			
			dm.districts[type] shouldBe 4
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		context("Build task") {
			
			test("Amount updates") {
			
				val type = DistrictType("1", starting = true)
				
				
				val dm = DistrictManager(setOf(type), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				val task = dm.createConstructionTask(type, ConstructionType.BUILD, 1)
				task.runOnComplete()
				
				dm.districts[type] shouldBe 1
			
			}
			
			test("Income updates") {
				
				val type = DistrictType(
					"1", starting = true,
					production = mapOf(ResourceType("1") to 4),
					upkeep = mapOf(ResourceType("1") to 1)
				)
				val income = ResourceIncome()
				val dm = DistrictManager(setOf(type), income, mockk(relaxUnitFun = true))
				val expected = mapOf(ResourceType("1") to 3)
				val task = dm.createConstructionTask(type, ConstructionType.BUILD, 1)
				
				task.runOnComplete()
				
				income.income.shouldContainExactly(expected)
				
			}
			
			test("Jobs updates") {
				
				val type = DistrictType(
					"1", starting = true,
					jobs = mapOf(JobType("test") to 1)
				)
				val jobs = JobManager(mockk(relaxUnitFun = true))
				val dm = DistrictManager(setOf(type), mockk(relaxUnitFun = true), jobs)
				val expected = mapOf(JobType("test") to 1)
				val task = dm.createConstructionTask(type, ConstructionType.BUILD, 1)
				
				task.runOnComplete()
				
				jobs.jobs.shouldContainExactly(expected)
				
			}
			
		}
		
	}
	
})