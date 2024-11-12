package com.br.biometric_tool.infra.database

import com.br.biometric_tool.infra.exceptions.DatabaseNotConnectedException
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

