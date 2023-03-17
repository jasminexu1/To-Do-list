import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class dynamodbTest {
    val task1 = Tisk(
        duedate = "10-22-2032",
        text = "test123",
        uuid = "alpha223"
    )

    val task2 = Tisk(
        duedate = "10-22-2032",
        text = "test1234",
        uuid = "alpha2234"
    )

    val task3 = Tisk(
        uuid = "task3test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-18-2032"
    )
    fun addTisk(tisk: Tisk) {
        return
    }

    val task4 = Tisk(
        uuid = "task4test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-19-2032"
    )

    val task5 = Tisk(
        uuid = "task5test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-20-2032"
    )

    val task6 = Tisk(
        uuid = "task6test",
        tag = "red",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-21-2032"
    )

    val task7 = Tisk(
        uuid = "task7test",
        tag = "green",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-22-2032"
    )

    val task8 = Tisk(
        uuid = "task8test",
        tag = "red",
        priority = "2",
        description = "testing",
        text = "whatever",
        duedate = "11-23-2032"
    )

    val task9 = Tisk(
        uuid = "task9test",
        tag = "green",
        priority = "3",
        description = "testing",
        text = "whatever",
        duedate = "11-24-2032"
    )

    val task10 = Tisk(
        uuid = "task10test",
        tag = "red",
        priority = "2",
        description = "testing",
        text = "whatever",
        duedate = "12-25-2032"
    )

    val task11 = Tisk(
        uuid = "task11test",
        tag = "green",
        priority = "3",
        description = "testing",
        text = "whatever",
        duedate = "11-26-2032"
    )

    val task12 = Tisk(
        uuid = "task12test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-27-2032"
    )

    val task13 = Tisk(
        uuid = "task13test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-28-2032"
    )

    val task14 = Tisk(
        uuid = "task14test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-29-2032"
    )

    val task15 = Tisk(
        uuid = "task15test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-30-2032"
    )

    val db = dynamodb()
    val user = userHandler()
    val daodb = DAOFacadeImpl()
    val archivedb = ArchiveFacadeImpl()
    val state = true

    @Test
    fun getUser() = runBlocking {
        DatabaseFactory.init()
        ArchiveFactory.init()
        async {
            user.loginUser("seongamsai@foxmail.com", "Unittest1!")
            user.initiateAuth("seongamsai@foxmail.com", "Unittest1!")
            addTisk(task1)
        }
        db.getUser("seongamsai@foxmail.com")
        val mylist = ArrayList<Tisk>()
        mylist.add(task1)
        val taglist = ArrayList<String>()
        taglist.add("red")
        taglist.add("yellow")
        taglist.add("blue")
        taglist.add("green")
        taglist.add("untagged, ")
        val prilist = ArrayList<String>()
        prilist.add("1")
        prilist.add("2")
        prilist.add("3")
        prilist.add("4")
        prilist.add("5")
        prilist.add("6")
        prilist.add("7")
        prilist.add("8")
        prilist.add("9")
        prilist.add("10, ")
        val itemlist = ArrayList<Items>()
        val result3 = Items(uuid = "seongamsai@foxmail.com", Archive = mylist, CustomTags = taglist, CustomPriorities = prilist, Tasks = mylist)
        itemlist.add(result3)
        val result = jsonConverter(Items = itemlist, Count = 1, ScannedCount = 1)





        val result1 = db.getUser("seongamsai@foxmail.com")

        assertEquals(result, result1)
    }

    @Test
    fun putUser() = runBlocking{

        DatabaseFactory.init()
        ArchiveFactory.init()
        async {
            user.loginUser("seongamsai@foxmail.com", "Unittest1!")
            user.initiateAuth("seongamsai@foxmail.com", "Unittest1!")
            addTisk(task1)
        }
        db.getUser("seongamsai@foxmail.com")
        val mylist = ArrayList<Tisk>()
        mylist.add(task1)
        val taglist = ArrayList<String>()
        taglist.add("red")
        taglist.add("yellow")
        taglist.add("blue")
        taglist.add("green")
        taglist.add("untagged, ")
        val prilist = ArrayList<String>()
        prilist.add("1")
        prilist.add("2")
        prilist.add("3")
        prilist.add("4")
        prilist.add("5")
        prilist.add("6")
        prilist.add("7")
        prilist.add("8")
        prilist.add("9")
        prilist.add("10, ")
        val itemlist = ArrayList<Items>()
        val result3 = Items(uuid = "seongamsai@foxmail.com", Archive = mylist, CustomTags = taglist, CustomPriorities = prilist, Tasks = mylist)
        itemlist.add(result3)
        val result = jsonConverter(Items = itemlist, Count = 1, ScannedCount = 1)





        val result1 = db.getUser("seongamsai@foxmail.com")




        assertEquals(result, result1)
    }
}
/*
internal class dynamodbTest {

    val testdb = dynamodb()


    val task1 = Tisk(
        duedate = "10-22-2032",
        text = "test123",
        uuid = "alpha223"
    )

    val task2 = Tisk(
        duedate = "10-22-2032",
        text = "test1234",
        uuid = "alpha2234"
    )

    val task3 = Tisk(
        uuid = "task3test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-18-2032"
    )

    val task4 = Tisk(
        uuid = "task4test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-19-2032"
    )

    val task5 = Tisk(
        uuid = "task5test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-20-2032"
    )

    val task6 = Tisk(
        uuid = "task6test",
        tag = "red",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-21-2032"
    )

    val task7 = Tisk(
        uuid = "task7test",
        tag = "green",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-22-2032"
    )

    val task8 = Tisk(
        uuid = "task8test",
        tag = "red",
        priority = "2",
        description = "testing",
        text = "whatever",
        duedate = "11-23-2032"
    )

    val task9 = Tisk(
        uuid = "task9test",
        tag = "green",
        priority = "3",
        description = "testing",
        text = "whatever",
        duedate = "11-24-2032"
    )

    val task10 = Tisk(
        uuid = "task10test",
        tag = "red",
        priority = "2",
        description = "testing",
        text = "whatever",
        duedate = "12-25-2032"
    )

    val task11 = Tisk(
        uuid = "task11test",
        tag = "green",
        priority = "3",
        description = "testing",
        text = "whatever",
        duedate = "11-26-2032"
    )

    val task12 = Tisk(
        uuid = "task12test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-27-2032"
    )

    val task13 = Tisk(
        uuid = "task13test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-28-2032"
    )

    val task14 = Tisk(
        uuid = "task14test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-29-2032"
    )

    val task15 = Tisk(
        uuid = "task15test",
        tag = "untagged",
        priority = "1",
        description = "testing",
        text = "whatever",
        duedate = "11-30-2032"
    )

    @Test
    fun getItemsDueDate() {
        //test1
        assertNotEquals(null, testdb.getItemsDueDate("10-22-2032"))
        val result1: Items = Items(Items = ArrayList<Tisk>(), Count = 0, ScannedCount = 0)
        assertEquals(result1, testdb.getItemsDueDate("10-22-2034"))

        //test2
        testdb.putItem(task1)
        val mylist = ArrayList<Tisk>()
        mylist.add(task2)
        mylist.add(task1)
        val result2: Items = Items(Items = mylist, Count = 2, ScannedCount = 2)
        assertEquals(result2, testdb.getItemsDueDate("10-22-2032"))
    }

    @Test
    fun putItem() {
        val myStatus = 200
        assertEquals(myStatus, testdb.putItem(task1)) //test1
        assertEquals(myStatus, testdb.putItem(task2)) //test1

        //we shouldnt be able to find test 1 since it is untagged
        var mybool2 = false
        val mylist5 = testdb.getItemsTag("red")
        if (mylist5 != null) {
            if (mylist5.Items.contains(task1))
                mybool2 = true
        }
        assertEquals(false, mybool2)

        //we should be able to find task 2 since it is untagged and added
        var mybool = false
        val mylist4 = testdb.getItemsTag("untagged")
        if (mylist4 != null) {
            if (mylist4.Items.contains(task2))
                mybool = true
        }
        assertEquals(true, mybool)
    }

    @Test
    fun deleteItemsUuid() {
        val myStatus = 200
        assertEquals(myStatus, testdb.deleteItemsUuid("alpha2234")) //test1
        assertEquals(myStatus, testdb.deleteItemsUuid("alpha223")) // test2

        //task 15 has not been deleted, so it should get a true return
        testdb.putItem(task15)
        var mybool = false
        val mylist4 = testdb.getItemsDueDate("11-30-2032")
        if (mylist4 != null) {
            if (mylist4.Items.contains(task15))
                mybool = true
        }
        assertEquals(true, mybool)

        // task 2 gets deleted completely, we should get false here
        var mybool2 = false
        val mylist5 = testdb.getItemsDueDate("10-22-2032")
        if (mylist5 != null) {
            if (mylist5.Items.contains(task2))
                mybool2 = false
        }
        assertEquals(false, mybool2)
    }



    @Test
    fun getArchive() {
        //we put item in database then put it to archive and see if
        //it actually is in archive
        testdb.putItem(task4)
        testdb.putArchive(task4)
        var mybool = false
        val mylist4 = testdb.getArchive()
        if (mylist4 != null) {
            if (mylist4.Items.contains(task4))
            mybool = true
        }
        assertEquals(true, mybool)


        //we dont put task 5, so we expect it to not be in archive
        testdb.putItem(task5)
        var mybool2 = false
        val mylist5 = testdb.getArchive()
        if (mylist5 != null) {
            if (mylist5.Items.contains(task5))
                mybool2 = true
        }
        assertEquals(false, mybool2)
    }


    @Test
    fun getItemsTag() {
        //we put task6(red) in and see what getItemsTag returns, we should get true
        testdb.putItem(task6)
        var mybool = false
        val mylist4 = testdb.getItemsTag("red")
        if (mylist4 != null) {
            if (mylist4.Items.contains(task6))
                mybool = true
        }
        assertEquals(true, mybool)

        //we put item 7 but since its tag is green, it will not be found
        //should return false
        testdb.putItem(task7)
        var mybool2 = false
        val mylist5 = testdb.getItemsTag("red")
        if (mylist5 != null) {
            if (mylist5.Items.contains(task7))
                mybool2 = true
        }
        assertEquals(false, mybool2)
    }


    @Test
    fun putArchive() {
        //test1 to see if there is error
        val myStatus = 200
        testdb.putItem(task3)
        assertEquals(myStatus, testdb.putArchive(task3))

        //test 2 to see if it actually gets put into archive
        val mylist3 = ArrayList<Tisk>()
        mylist3.add(task3)
        val result3: Items = Items(Items = mylist3, Count = 1, ScannedCount = 1)
        assertEquals(result3, testdb.getItemsDueDate("11-18-2032"))

        //task 14 is put but not in archive yet, should return false
        testdb.putItem(task14)
        var mybool = false
        val mylist7 = testdb.getArchive()
        if (mylist7 != null) {
            if (mylist7.Items.contains(task14))
                mybool = true
        }
        assertEquals(false, mybool)

        //put task12 in archive and test if it is in archive list
        //should return true
        testdb.putArchive(task12)
        mybool = false
        val mylist4 = testdb.getArchive()
        if (mylist4 != null) {
            if (mylist4.Items.contains(task12))
                mybool = true
        }
        assertEquals(true, mybool)

        //task 13 is put in archive, should return true
        testdb.putItem(task13)
        testdb.putArchive(task13)
        var mybool2 = false
        val mylist5 = testdb.getArchive()
        if (mylist5 != null) {
            if (mylist5.Items.contains(task13))
                mybool2 = true
        }
        assertEquals(true, mybool2)

        //task13 is deleted from archive, should return false now
        testdb.deleteArchiveUuid("task13test")
        mybool2 = false
        val mylist8 = testdb.getArchive()
        if (mylist8 != null) {
            if (mylist8.Items.contains(task13))
                mybool2 = true
        }
        assertEquals(false, mybool2)
    }

    @Test
    fun getItemsPriority() {

        // we add task 8 that has a priority of 2 in
        // if it is in the resulting list then return true
        testdb.putItem(task8)
        var mybool = false
        val mylist4 = testdb.getItemsPriority("2")
        if (mylist4 != null) {
            if (mylist4.Items.contains(task8))
                mybool = true
        }
        assertEquals(true, mybool)

        //we put item 9 with priority 3 in,
        //it should return false since we are getting items with priority of 2
        testdb.putItem(task9)
        var mybool2 = false
        val mylist5 = testdb.getItemsPriority("2")
        if (mylist5 != null) {
            if (mylist5.Items.contains(task9))
                mybool2 = true
        }
        assertEquals(false, mybool2)
    }

    @Test
    fun deleteArchiveUuid() {
        val myStatus = 200
        testdb.putItem(task10)
        testdb.putItem(task11)
        testdb.putArchive(task10)
        testdb.putArchive(task11)

        assertEquals(myStatus, testdb.deleteArchiveUuid("task10test")) //test1
        assertEquals(myStatus, testdb.deleteArchiveUuid("task11test")) // test2

        var mybool2 = false
        val mylist5 = testdb.getItemsDueDate("12-25-2032")
        if (mylist5 != null) {
            if (mylist5.Items.contains(task10))
                mybool2 = true
        }
        assertEquals(true, mybool2)

        //if it gets deleted completely, now we expect a false
        testdb.deleteItemsUuid("task10test")
        var mybool3 = false
        val mylist6 = testdb.getItemsDueDate("11-25-2032")
        if (mylist6 != null) {
            if (mylist6.Items.contains(task10))
                mybool3 = true
        }
        assertEquals(false, mybool3)
    }
}

 */
