package com.example.delivery_app.repositories

import android.app.Application
import android.content.Context
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.delivery_app.FakeConstants
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.GetOrdersResponse
import com.example.delivery_app.models.RoomDeliveryOrder
import com.example.delivery_app.services.MockApiFakeRetrofitService
import com.example.delivery_app.services.room.OrderRoomDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.typeOf
import org.mockito.Mockito.`when`




@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class RoomOrderRepositoryImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: IDeliveryOrderRepository
    private lateinit var getRoomDeliveryOrdersFromDeliveryOrders: Method
    private lateinit var getDeliveryOrdersFromRoomDeliveryOrders: Method

    private lateinit var fakeRetrofitService: Field

    @Mock
    var mockApplication: Application? = null

    @Mock
    var mockAndroidContext: Context? = null

    @Before
    fun setupRepository() {
        mockAndroidContext  =  Mockito.mock(Context::class.java)
        mockApplication = Mockito.mock(Application::class.java)

        `when`(mockAndroidContext!!.applicationContext).thenReturn(mockApplication)
        mockRepository = RoomOrderRepositoryImpl(OrderRoomDatabase.getDatabase(mockAndroidContext!!).orderDao())

        val collectionDeliveryType = (typeOf<List<DeliveryOrder>>().classifier!! as KClass<List<DeliveryOrder>>).java
        getRoomDeliveryOrdersFromDeliveryOrders = mockRepository.javaClass.getDeclaredMethod("getRoomDeliveryOrdersFromDeliveryOrders", collectionDeliveryType)
        getRoomDeliveryOrdersFromDeliveryOrders.isAccessible = true

        val collectionRoomType = (typeOf<List<RoomDeliveryOrder>>().classifier!! as KClass<List<RoomDeliveryOrder>>).java
        getDeliveryOrdersFromRoomDeliveryOrders = mockRepository.javaClass.getDeclaredMethod("getDeliveryOrdersFromRoomDeliveryOrders", collectionRoomType)
        getDeliveryOrdersFromRoomDeliveryOrders.isAccessible = true
    }

    @Test
    fun getOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        //val value = getRoomDeliveryOrdersFromDeliveryOrders(mockRepository, FakeConstants.mockResponse)
        //Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

}