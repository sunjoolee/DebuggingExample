package learningconcurrencyinkotlin.debuggingexample

class UserManager(private val dataSource: DataSource) {
    suspend fun getUser(id:Int) : User{
        val name = dataSource.getNameAsync(id)
        val age = dataSource.getAgeAsync(id)
        val profession = dataSource.getProfessionAsync(id)

        //profession을 받기 위해 대기, 더 오래 걸리기 때문
        profession.await()

        return User(name.getCompleted(), age.getCompleted(), profession.getCompleted())
    }
}