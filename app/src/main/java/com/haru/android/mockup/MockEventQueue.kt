package com.haru.android.mockup

import com.haru.android.Dailiy.DTO.Event

object MockEventQueue  {
    private val eventQueue: ArrayDeque<Event> = ArrayDeque()

    fun pushEvent(event: Event) {
        eventQueue.addLast(event)
    }

    fun popEvent(): Event? {
        return if (eventQueue.isNotEmpty()) {
            eventQueue.removeFirst()
        } else {
            null
        }
    }

    // 전체 이벤트 리스트 반환
    fun getEvents(): List<Event> {
        return eventQueue.toList()
    }

}