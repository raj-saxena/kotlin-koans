package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

    operator fun plus(timeInterval: TimeInterval): MyDate =
        this.addTimeIntervals(timeInterval, 1)

    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)

    operator fun plus(intervalPeriod: IntervalPeriod): MyDate =
        addTimeIntervals(intervalPeriod.timeInterval, intervalPeriod.number)

}


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(times: Int): IntervalPeriod = IntervalPeriod(this, times)
}

class IntervalPeriod(val timeInterval: TimeInterval, val number: Int)

class DateRange(private val start: MyDate, private val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start
            override fun hasNext(): Boolean = current <= endInclusive


            override fun next(): MyDate {
                val nextDay = current
                current = current.nextDay()
                return nextDay
            }

        }
    }


    operator fun contains(other: MyDate): Boolean = start <= other && other <= endInclusive
}
