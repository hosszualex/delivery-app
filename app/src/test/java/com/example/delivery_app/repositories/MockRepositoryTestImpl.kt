package com.example.delivery_app.repositories

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.delivery_app.FakeConstants
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.models.GetOrdersResponse
import com.example.delivery_app.services.MockApiFakeRetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class MockRepositoryTestImpl {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: IDeliveryOrderRepository
    private lateinit var method: Method
    private lateinit var fakeRetrofitService: Field

    @Before
    fun setupRepository() {
        mockRepository = MockApiRepositoryImpl()
        val collectionType = (typeOf<ArrayList<GetOrdersResponse>>().classifier!! as KClass<ArrayList<GetOrdersResponse>>).java
        method = mockRepository.javaClass.getDeclaredMethod("getDeliveryOrdersFromResponse", collectionType)
        method.isAccessible = true

        fakeRetrofitService = mockRepository.javaClass.getDeclaredField("retrofitService")
        fakeRetrofitService.isAccessible = true
        fakeRetrofitService.set(mockRepository, MockApiFakeRetrofitService)
    }

    @Test
    fun getOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, FakeConstants.mockResponse)
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun getEmptyOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, arrayListOf<GetOrdersResponse>())
        Assert.assertEquals(true, (value as List<DeliveryOrder>).isEmpty())
    }

    @Test
    fun onGetRepositoryOrdersSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockApiFakeRetrofitService.mockData = FakeConstants.mockResponse
        MockApiFakeRetrofitService.responseCode = 200

        runBlocking(Dispatchers.IO) {
            mockRepository.getDeliveryOrders(object: IDeliveryOrderRepository.IOnGetDeliveryOrders{
                override fun onSuccess(orders: List<DeliveryOrder>) {
                    Assert.assertEquals(FakeConstants.expectedMockResponse, orders)
                }

                override fun onFailed(error: ErrorResponse) {}
            })
        }
    }

    @Test
    fun onGetRepositoryOrdersFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockApiFakeRetrofitService.responseCode = 400
        runBlocking(Dispatchers.IO) {
            mockRepository.getDeliveryOrders(object: IDeliveryOrderRepository.IOnGetDeliveryOrders{
                override fun onSuccess(orders: List<DeliveryOrder>) {}

                override fun onFailed(error: ErrorResponse) {
                    Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
                }
            })
        }
    }

}