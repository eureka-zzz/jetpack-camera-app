/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.jetpackcamera.ui.controller.impl

import com.google.common.truth.Truth.assertThat
import com.google.jetpackcamera.core.camera.testing.FakeCameraSystem
import com.google.jetpackcamera.model.DynamicRange
import com.google.jetpackcamera.model.ExternalCaptureMode
import com.google.jetpackcamera.model.ImageOutputFormat
import com.google.jetpackcamera.ui.uistate.capture.TrackedCaptureUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuickSettingsControllerImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun setDynamicRangeAndImageFormat_appliesHdrSettingsAtomically() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val cameraSystem = FakeCameraSystem()
        val trackedCaptureUiState = MutableStateFlow(TrackedCaptureUiState())

        val controller = QuickSettingsControllerImpl(
            trackedCaptureUiState = trackedCaptureUiState,
            cameraSystem = cameraSystem,
            externalCaptureMode = ExternalCaptureMode.Standard,
            coroutineContext = dispatcher
        )

        controller.setDynamicRangeAndImageFormat(DynamicRange.HLG10, ImageOutputFormat.JPEG_ULTRA_HDR)

        advanceUntilIdle()

        assertThat(cameraSystem.getCurrentSettings().value?.dynamicRange)
            .isEqualTo(DynamicRange.HLG10)
        assertThat(cameraSystem.getCurrentSettings().value?.imageFormat)
            .isEqualTo(ImageOutputFormat.JPEG_ULTRA_HDR)
    }
}
