package com.example.locationreminder.locationreminder.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.locationreminder.locationreminder.data.db.ReminderDatabase
import com.example.locationreminder.locationreminder.data.repository.ReminderRepositoryImpl
import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource
import com.example.locationreminder.locationreminder.data.service.ReminderService
import com.example.locationreminder.locationreminder.domain.NotificationService
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository
import com.example.locationreminder.locationreminder.domain.usecases.GetAllRemindersUseCase
import com.example.locationreminder.locationreminder.domain.usecases.HandleGeofenceTransitionUseCase
import com.example.locationreminder.locationreminder.domain.usecases.RegisterAllGeofencesUseCase
import com.example.locationreminder.locationreminder.domain.usecases.RegisterGeofenceUseCase
import com.example.locationreminder.locationreminder.domain.usecases.RemoveGeofenceUseCase
import com.example.locationreminder.locationreminder.domain.usecases.SaveReminderUseCase
import com.example.locationreminder.locationreminder.presentation.geofencing.GeofencingAppObserver
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreenViewmodel
import com.example.locationreminder.locationreminder.presentation.notification.NotificationServiceImpl
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreenViewmodel
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingFactory
import com.mapbox.common.geofencing.GeofencingService
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@OptIn(MapboxExperimental::class)
val appModule = module {

  single<GeofencingAppObserver> {
    GeofencingAppObserver(get())
  }


  single { RegisterGeofenceUseCase(get()) }
  single { RemoveGeofenceUseCase(get()) }

  single<GeofencingService> {
    GeofencingFactory.getOrCreate()
  }

  single {
    Room.databaseBuilder(
      get<Context>(),
      ReminderDatabase::class.java,
      "reminder-db"
    ).build()
  }


  single {
    get<ReminderDatabase>().reminderDao()
  }
  single<ReminderRepository> {
    ReminderRepositoryImpl(
      reminderService = get(),
      geofenceApiSource = get()
    )
  }
    single {
        ReminderService(get())
    }

  single <NotificationService>{
    NotificationServiceImpl(get())
  }
  single {
    GeofencingFactory.getOrCreate()
  }

  single {
    GeofenceAppSource(get())
  }

    factory {
        GetAllRemindersUseCase(get())
    }

    factory {
        SaveReminderUseCase(get())
    }

    factory {
      HandleGeofenceTransitionUseCase(get(), get(),get())
    }

    factory {
      RegisterAllGeofencesUseCase(get(),get())
    }

    viewModel {
      MapBoxScreenViewmodel(saveReminderUseCase = get() )

    }

    viewModel {
      ReminderListScreenViewmodel(getAllRemindersUseCase = get())

    }

  }
