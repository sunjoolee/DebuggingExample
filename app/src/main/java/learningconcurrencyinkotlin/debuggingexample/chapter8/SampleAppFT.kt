package learningconcurrencyinkotlin.debuggingexample.chapter8

import kotlinx.coroutines.*
import learningconcurrencyinkotlin.debuggingexample.DataSource
import learningconcurrencyinkotlin.debuggingexample.UserManager
import org.junit.Assert.assertTrue

import org.junit.Test
import java.util.Calendar

class SampleAppFT {
    @Test
    fun testHappyPath() = runBlocking {
        val manager = UserManager(MockDataSource())
        val user = manager.getUser(10)

        assertTrue(user.name == "Susan Calvin")
        assertTrue(user.age ==  Calendar.getInstance().get(Calendar.YEAR) - 1982)
        assertTrue(user.profession == "Robopsychologist")
    }
    @Test
    fun testOppositeOrder() = runBlocking {
        //TODO: 예상이 어려운 검색 순서에 대한 테스트 구현
    }
}

class MockDataSource:DataSource{
    //데이터베이스에서 name을 가져오는 모의 기능
    override fun getNameAsync(id: Int) = GlobalScope.async{
        delay(200)
        "Susan Calvin"
    }
    //캐시에서 age를 가져오는 모의 기능
    override fun getAgeAsync(id: Int) = GlobalScope.async{
        delay(500)
        Calendar.getInstance().get(Calendar.YEAR) - 1982
    }
    //외부시스템에서 profession을 가져오는 모의 기능
    override fun getProfessionAsync(id: Int) = GlobalScope.async{
        delay(2000)
        "Robopsychologist"
    }
}