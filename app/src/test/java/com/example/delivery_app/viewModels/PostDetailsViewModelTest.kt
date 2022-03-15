//package com.example.delivery_app.viewModels
//
//import android.os.Looper
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.delivery_app.models.ErrorResponse
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.Shadows
//import org.robolectric.annotation.LooperMode
//import java.lang.reflect.Field
//
//@RunWith(AndroidJUnit4::class)
//@LooperMode(LooperMode.Mode.PAUSED)
//class PostDetailsViewModelTest {
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var postDetailsViewModel: PostDetailsViewModel
//    private lateinit var fakeSocialMediaRepository: Field
//
//    @Before
//    fun setupViewModel() {
//        postDetailsViewModel = PostDetailsViewModel()
//        fakeSocialMediaRepository = postDetailsViewModel.javaClass.getDeclaredField("socialMediaRepository")
//        fakeSocialMediaRepository.isAccessible = true
//        fakeSocialMediaRepository.set(postDetailsViewModel, InstagramRepositoryFakeImpl())
//    }
//
//    @Test
//    fun onGetPostsSuccessTest() {
////        Shadows.shadowOf(Looper.getMainLooper()).idle()
////        IInstagramRetrofitFakeService.mockData = FakeConstants.mockResponse
////        IInstagramRetrofitFakeService.responseCode = 200
////
////        postDetailsViewModel.retrieveChildrenPosts(FakeConstants.mockCarouselId)
////        val value = postDetailsViewModel.onGetPosts.value
////        Assert.assertEquals(FakeConstants.expectedMockChildrenValue, value)
//    }
//}