package eweise.midifun

class Scale(intervals: List[Int]) {
  def chord(root: Int, key: Int): List[Int] =
    val keyFromC = key % 12
    val rootFromC = root % 12
    val diffRootFromKey = math.abs(rootFromC - keyFromC)

    // figure out where we are in the scale
    var locInScale = diffRootFromKey
    val tmpIntervals = LoopingList(intervals)

    while (locInScale > 0)
      val value = tmpIntervals.increment()
      locInScale -= value

    val third = root + tmpIntervals.increment() + tmpIntervals.increment()
    val fifth = third + tmpIntervals.increment() + tmpIntervals.increment()
    List(root, third, fifth)
}

class Major extends Scale(intervals = List(2, 2, 1, 2, 2, 2, 1))

class Minor extends Scale(intervals = List(2, 1, 2, 2, 2, 1, 2))

class LoopingList(val originalList: List[Int]) {
  var mutatingList = originalList

  def increment(): Int =
    val index = 0
    if mutatingList.isEmpty then
      mutatingList = originalList

    val result = mutatingList.head
    mutatingList = mutatingList.tail
    result
}