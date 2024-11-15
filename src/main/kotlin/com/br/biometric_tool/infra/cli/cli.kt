package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.core.dto.ChangeBiometricStatusInput
import com.br.biometric_tool.core.dto.GetBiometricsEnabledInput
import com.br.biometric_tool.core.dto.LoginInput
import com.br.biometric_tool.core.dto.SignupInput
import com.br.biometric_tool.core.service.ChangeBiometricStatus
import com.br.biometric_tool.core.service.GetBiometricsEnabled
import com.br.biometric_tool.core.service.Login
import com.br.biometric_tool.core.service.Signup
import com.br.biometric_tool.infra.exceptions.InvalidStatusException
import com.br.biometric_tool.infra.exceptions.LoginFailedException
import com.br.biometric_tool.infra.exceptions.TogglingBiometricsException
import com.br.biometric_tool.infra.exceptions.UnexpectedException
import org.opencv.core.Core
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
}
