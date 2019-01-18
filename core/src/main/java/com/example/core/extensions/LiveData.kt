package com.example.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

operator fun <T> MutableLiveData<T>.invoke(newValue: T) {
    value = newValue
}

fun <T> MutableLiveData<T>.startWith(startValue: T): MutableLiveData<T> {
    value = startValue
    return this
}

fun <T> Fragment.subscribe(liveData: LiveData<T>, action: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        action(it)
    })
}

fun <T> LiveData<T>.subscribe(lifecycle: LifecycleOwner, action: (T?) -> Unit) {
    observe(lifecycle, Observer {
        action(it)
    })
}

fun <T> LiveData<T>.unsubscribe(lifecycleOwner: LifecycleOwner) = removeObservers(lifecycleOwner)

fun <T> LiveData<T>.filter(predicate: (T) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()

    result.addSource(this) {
        if (predicate(it!!)) result.value = it
    }

    return result
}