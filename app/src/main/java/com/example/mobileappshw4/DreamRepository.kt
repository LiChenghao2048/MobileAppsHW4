package com.example.mobileappshw4

import kotlinx.coroutines.flow.Flow

class DreamRepository (private val dreamDAO: DreamDAO) {

    // Room does not run queries on main thread
    // it required you to run query on a separate thread

    // store all the results into a list and make it a public property that

    // for each of the method in the DAO, you need to write something to execute them in separate threads

    // getSortedDreams
    val allDreams:Flow<List<Dream>> = dreamDAO.getSortedDreams()

    // suspend -> room runs all suspend functions/queries off the main thread
    // so we are just call it and embed in a method that we can use later

    suspend fun insert (dream: Dream){
        dreamDAO.insert(dream)
    }

    suspend fun delete (id: Int){
        dreamDAO.delete(id)
    }

    suspend fun update (title: String, content: String, reflection: String, emotion: String, id: Int) {
        dreamDAO.update(title, content, reflection, emotion, id)
    }

    suspend fun getDream(id: Int) : Dream{
        return dreamDAO.getDream(id)
    }

}