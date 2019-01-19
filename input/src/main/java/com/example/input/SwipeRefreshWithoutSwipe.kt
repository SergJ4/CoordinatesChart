package com.example.input

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.dip


private const val DEFAULT_OFFSET = 60

class SwipeRefreshWithoutSwipe : SwipeRefreshLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    private fun init() {
        setSize(SwipeRefreshLayout.DEFAULT)
        setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_blue_light)
        setDistanceToTriggerSync(200)
        setProgressViewOffset(true, 0, dip(DEFAULT_OFFSET))
    }

    override fun canChildScrollUp(): Boolean = true
}