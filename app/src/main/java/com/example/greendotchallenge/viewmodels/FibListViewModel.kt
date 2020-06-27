package com.example.greendotchallenge.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.greendotchallenge.models.FibModel
import kotlinx.coroutines.*

object FibListViewModel {


    var startSysTime: Long = 0
    var finishSysTime: Long = 0

    //list of fib objects
    private val fibList2: MutableLiveData<MutableList<FibModel>> by lazy {
        MutableLiveData<MutableList<FibModel>>().also {
            it.value = mutableListOf()
        }
    }


    //public method for taking in user input and build fib object to populate list of fib objects
    fun setFibData(number: Int) {
        var tempMap = emptyMap<Int, Int>()
        startSysTime = System.currentTimeMillis()

        computeFib(number) {
            tempMap = it
            finishSysTime = System.currentTimeMillis()
            fibList2.value!!.add(FibModel(number, tempMap, finishSysTime - startSysTime))
            fibList2.postValue(fibList2.value)
        }
    }

    //iterates to the user input value to calculate fib
    private suspend fun allFib(number: Int): Map<Int, Int> {
        var tempMap = emptyMap<Int, Int>()
        for (i in 0..number) {
            tempMap = tempMap + Pair(i, fib(i))
        }
        return tempMap
    }

    //recursive function for calculating fib
    private suspend fun fib(number: Int):  Int {
        if (number <= 0) return 0
        else if (number == 1) return 1
        return fib(number - 1) + fib(number - 2)
    }




    //Here is a coroutine builder called async which allows app to perform an asynchronous operation returning a value
   private fun computeFib(number:Int, callBack: (Map<Int, Int>) -> Unit){
         val tempMap: Deferred<Map<Int, Int>> = GlobalScope.async {//Threads and coroutines are something I need to work on more, there is room for improvement here, i am aware that globalscope.async in not preferred
            allFib(number)
        }
        //gives the ability to get a callback value from the coroutine so the data can be populated on the main thread
        CoroutineScope(Dispatchers.IO).launch {
           val tempMap2 = tempMap.await()
            withContext(Dispatchers.Main) {
               callBack(tempMap2)
            }
        }
    }


    //public getter method for handling retrieving live data from view model
    fun getFibList(): MutableLiveData<MutableList<FibModel>> = fibList2

}