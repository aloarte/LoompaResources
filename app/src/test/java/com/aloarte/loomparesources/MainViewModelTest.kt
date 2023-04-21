package com.aloarte.loomparesources

import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.ui.UiEvent
import com.aloarte.loomparesources.ui.UiState
import com.aloarte.loomparesources.ui.state.ScreenStatus
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel
import com.aloarte.loomparesources.utils.CoroutinesTestRule
import com.aloarte.loomparesources.utils.TestData.ID
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompa
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var repository: WillyWonkaRepository

    private lateinit var viewModel: MainViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `test view model on event load home`() = coroutinesTestRule.runBlockingTest {
        viewModel.onEvent(UiEvent.LoadHome)

        val expectedState = UiState(
            detailRequested = ScreenStatus.NotRequested,
            homeRequested = ScreenStatus.Requested,
            oompaLoompaDetailId = -1,
            oompaLoompaDetail = null,
            errorMessage = null
        )
        Assert.assertEquals(expectedState, viewModel.state.first())
    }

    @Test
    fun `test view model on event load detail success`() = coroutinesTestRule.runBlockingTest {
        coEvery { repository.getOompaLoompa(ID) } returns detailedOompaLoompa

        viewModel.onEvent(UiEvent.LoadDetail(ID))

        coVerify { repository.getOompaLoompa(ID) }
        val expectedStateDetailResult = UiState(
            detailRequested = ScreenStatus.Success,
            homeRequested = ScreenStatus.NotRequested,
            oompaLoompaDetail = detailedOompaLoompa,
            oompaLoompaDetailId = ID,
            errorMessage = null
        )
        Assert.assertEquals(expectedStateDetailResult, viewModel.state.first())
    }

    @Test
    fun `test view model on event load detail failed`() = coroutinesTestRule.runBlockingTest {
        coEvery { repository.getOompaLoompa(ID) } returns null

        viewModel.onEvent(UiEvent.LoadDetail(ID))

        coVerify { repository.getOompaLoompa(ID) }
        val expectedStateDetailResult = UiState(
            detailRequested = ScreenStatus.Error,
            homeRequested = ScreenStatus.NotRequested,
            oompaLoompaDetail = null,
            oompaLoompaDetailId = ID,
            errorMessage = "Error loading the $ID employee"
        )
        Assert.assertEquals(expectedStateDetailResult, viewModel.state.first())
    }
}