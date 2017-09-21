package it.infinity.main

import scala.collection.mutable.ListBuffer

class Data {
	private var list = 
		ListBuffer(Item("Pasta", 1), Item("Carne", 2));
	
	def getItem(id: Long): Option[Item] = {
		list.find { x => x.id == id }
	}
	
	def addItem(item: Item): Unit = {
		list += item;
	}
	
}