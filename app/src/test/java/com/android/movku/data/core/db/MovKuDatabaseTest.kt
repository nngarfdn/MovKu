package com.android.movku.data.core.db
//package com.android.movku.data.core.db
//
//import android.content.Context
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.android.movku.data.auth.db.AuthDao
//import com.android.movku.data.auth.model.User
//import com.google.common.truth.Truth
//import junit.framework.TestCase
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.runBlockingTest
//import org.intellij.lang.annotations.Language
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import kotlin.random.Random
//
//@RunWith(AndroidJUnit4::class)
//class MovKuDatabaseTest: TestCase() {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var db: MovKuDatabase
//    private lateinit var dao: AuthDao
//
//    @Before
//    public override fun setUp() {
//        // get context -- since this is an instrumental test it requires
//        // context from the running application
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        // initialize the db and dao variable
//        db = Room.inMemoryDatabaseBuilder(context, MovKuDatabase::class.java).build()
//        dao = db.authDao()
//    }
//
//    // Override function closeDb() and annotate it with @After
//    // this function will be called at last when this test class is called
//    @After
//    fun closeDb() {
//        db.close()
//    }
//
//    // create a test function and annotate it with @Test
//    // here we are first adding an item to the db and then checking if that item
//    // is present in the db -- if the item is present then our test cases pass
//    @Test
//    fun registerTest() = runBlocking {
//        val user = User(899, "testing","testing@email.com", "testing",
//        "18 Agustus 2002","Bantul", null)
//        val insert = dao.insertUser(user)
//        assertTrue(insert > 0)
//    }
//
//    //test not success
//    @Test
//    fun loginTest() = runBlocking {
//        val user = User(username = "test", password = "test", email = "test@email.com", id = Random.nextInt())
//        val insert = dao.insertUser(user)
//        assertTrue(insert > 0)
//
////        assertNotNull(login)
//        assertEquals(dao.loginUser(user.email, user.password).username, user.username)
//    }
//
//
//}