class Solution {
    fun mostBooked(n: Int, meetings: Array<IntArray>): Int {
        val pq = PriorityQueue<Node>( compareBy<Node>{ it.endTime }.thenBy { it.roomId })
        val sorted = meetings.sortedWith( compareBy<IntArray> {it[0]} )
        for (i in 0 until n) {
            pq.add(Node(i, 0L, 0))
        }
        var maxUsed = 0
        var roomMaxUsed = n
        for (meeting in sorted) {
            val start = meeting[0].toLong()
            while(pq.peek().endTime < start) {
                val top = pq.poll()
                pq.add(Node(top.roomId, start, top.count))
            }
            val session = pq.poll()
            if (session.count > maxUsed ||
                (session.count == maxUsed && session.roomId < roomMaxUsed)) {
                    maxUsed = session.count
                    roomMaxUsed = session.roomId
            }
            val endTime = meeting[1].toLong() - start + session.endTime
            pq.add(Node(session.roomId, endTime, session.count+1))
        }
        return roomMaxUsed
    }

    data class Node(val roomId: Int, val endTime: Long, val count: Int)
}