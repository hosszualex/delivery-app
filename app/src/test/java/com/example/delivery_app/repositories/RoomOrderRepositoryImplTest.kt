package com.example.delivery_app.repositories

import CoroutineTestRule
import android.app.Application
import android.content.Context
import android.os.Looper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.delivery_app.FakeConstants
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.models.RoomDeliveryOrder
import com.example.delivery_app.services.room.OrderRoomDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.typeOf


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class RoomOrderRepositoryImplTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var mockRepository: IDeliveryOrderRepository
    private lateinit var getRoomDeliveryOrdersFromDeliveryOrders: Method
    private lateinit var getDeliveryOrdersFromRoomDeliveryOrders: Method
    private lateinit var database: OrderRoomDatabase

    @Mock
    var mockApplication: Application? = null

    @Mock
    var mockAndroidContext: Context? = null

    @Before
    fun setup() {
        mockAndroidContext = Mockito.mock(Context::class.java)
        mockApplication = Mockito.mock(Application::class.java)

        `when`(mockAndroidContext!!.applicationContext).thenReturn(mockApplication)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = OrderRoomDatabase.getDatabase(context)
        mockRepository = RoomOrderRepositoryImpl(database.orderDao())

        val collectionDeliveryType =
            (typeOf<List<DeliveryOrder>>().classifier!! as KClass<List<DeliveryOrder>>).java
        getRoomDeliveryOrdersFromDeliveryOrders = mockRepository.javaClass.getDeclaredMethod(
            "getRoomDeliveryOrdersFromDeliveryOrders",
            collectionDeliveryType
        )
        getRoomDeliveryOrdersFromDeliveryOrders.isAccessible = true

        val collectionRoomType =
            (typeOf<List<RoomDeliveryOrder>>().classifier!! as KClass<List<RoomDeliveryOrder>>).java
        getDeliveryOrdersFromRoomDeliveryOrders = mockRepository.javaClass.getDeclaredMethod(
            "getDeliveryOrdersFromRoomDeliveryOrders",
            collectionRoomType
        )
        getDeliveryOrdersFromRoomDeliveryOrders.isAccessible = true
    }

    @Test
    fun getOrdersFromResponseSuccessTest() = runBlocking {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value =
            getDeliveryOrdersFromRoomDeliveryOrders(mockRepository, FakeConstants.mockRoomResponse)
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun getRoomOrderFromDeliveryOrderSuccessTest() = runBlocking {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = getRoomDeliveryOrdersFromDeliveryOrders(
            mockRepository,
            FakeConstants.expectedMockResponse
        )
        Assert.assertEquals(FakeConstants.mockRoomResponse.sortedByDescending { it.price }, value)
    }

    @Test
    fun updateAndGetDeliveryOrdersSuccessTest() = runBlocking {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        mockRepository.updateAllDeliveryOrders(
            FakeConstants.expectedMockResponse,
            object : IDeliveryOrderRepository.IOnUpdateDeliveryOrder {
                override fun onSuccess() {
                    runBlocking {
                        mockRepository.getDeliveryOrders(object :
                            IDeliveryOrderRepository.IOnGetDeliveryOrders {
                            override fun onSuccess(orders: List<DeliveryOrder>) {
                                Assert.assertEquals(FakeConstants.expectedMockResponse, orders)
                            }

                            override fun onFailed(error: ErrorResponse) {}
                        })
                    }
                }
                override fun onFailed(error: ErrorResponse) {}
            })
    }

    @Test
    fun getEmptyDeliveryOrdersSuccessTest() = runBlocking {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        mockRepository.getDeliveryOrders(object :
            IDeliveryOrderRepository.IOnGetDeliveryOrders {
            override fun onSuccess(orders: List<DeliveryOrder>) {
                Assert.assertEquals(listOf<DeliveryOrder>(), orders)
            }

            override fun onFailed(error: ErrorResponse) {}
        })
    }

    @After
    fun tearDown() {
        database.close()
    }
}