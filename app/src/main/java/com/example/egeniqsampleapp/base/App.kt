package com.example.egeniqsampleapp.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class for the Egeniq Sample app.
 *
 * This class is responsible for initializing the application and setting up Hilt for dependency injection.
 * It is annotated with @HiltAndroidApp, which generates the necessary Hilt components and dependencies.
 * HiltAndroidApp is a class-level annotation that triggers Hilt's code generation.
 * It generates a base class for the application that serves as the application-level dependency container.
 * The generated class extends the Android application class and includes a generated component.
 *
 * @see Application
 * @see HiltAndroidApp
 */
@HiltAndroidApp
class App : Application()