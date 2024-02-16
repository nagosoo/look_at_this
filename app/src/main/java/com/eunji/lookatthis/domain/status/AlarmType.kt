package com.eunji.lookatthis.domain.status

enum class AlarmType(val time:String?) {
    EVERY_TIME(null),
    AM11("11:00"),
    PM15("15:00"),
    PM20("20:00")
}