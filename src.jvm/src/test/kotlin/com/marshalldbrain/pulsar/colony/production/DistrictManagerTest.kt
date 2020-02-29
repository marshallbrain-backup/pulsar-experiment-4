package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContainKeys
import io.kotlintest.matchers.maps.shouldNotContainExactly
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe

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
		
		val dm = DistrictManager(districts)
		
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
		
		val dm = DistrictManager(districts)
		
		dm.districts.keys.shouldContainExactly(expected)
		
	}
	
	context("Create task") {
		
		test("Multiple amount") {
			
			val type = DistrictType("1", 5, starting = true)
			
			val dm = DistrictManager(setOf(type))
			val cm = ConstructionManager()
			
			val task = dm.createConstructionTask(type, ConstructionType.BUILD, 4)
			cm.addToQueue(task)
			cm.tick(20)
			
			dm.districts[type] shouldBe 4
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		test("Build task") {
			
			val type = DistrictType("1", starting = true)
			
			val dm = DistrictManager(setOf(type))
			
			val task = dm.createConstructionTask(type, ConstructionType.BUILD, 1)
			task.runOnComplete()
			
			dm.districts[type] shouldBe 1
			
		}
		
	}
	
})