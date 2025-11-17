# 📍 Location Reminder — Android App  

A **modern Android application** that allows users to create **location-based reminders** that automatically trigger when entering a defined geographic area.

Built using **Jetpack Compose**, **Mapbox Maps & Geofencing SDK**, **Foreground Location Services**, **Koin DI**, **Room Database**, and **Kotlin Coroutines / Flow**.

---

## 🚀 Quick Start (Setup Instructions)

Before running the app, complete these steps:

### 1. Add your Mapbox token  
Open your `gradle.properties` file and add:
```
MAPBOX_DOWNLOADS_TOKEN=your_mapbox_token_here
```


### 2. Build & Install the app  
Run the project from Android Studio on a device.

### 3. Grant required permissions on first launch  
The app will request:
- Location (fine & coarse)
- Foreground Service (location)
- Post Notifications (Android 13+)

### 4. Foreground location monitoring auto-starts  
Once permissions are granted, the app begins monitoring your location in the background.

### 5. Start adding reminders from the map screen  
Tap anywhere on the map to create a location-based reminder.



---


## 🚀 Features

### 🌐 Interactive Map (Mapbox)
- Tap anywhere on the map to create a reminder  
- Displays Mapbox location puck  
- Reverse-geocodes coordinates into a readable address  
- Smooth camera transitions & marker support  

### 📌 Location-Based Reminders
- Real geofence registration using Mapbox Geofencing API  
- 300m radius around selected point  
- Triggered when entering a geofence  
- Dwell detection support  

### 🔔 Smart Notifications  
- Fires a notification when entering a geofenced zone  
- Compatible with Android 13+ (POST_NOTIFICATIONS)  
- High-priority notification channel  

### 🛰️ Foreground Location Monitoring
- Background location updates via **Foreground Service**  
- FusedLocationProviderClient for GPS updates  
- Automatically feeds Mapbox geofencing engine  

### 📦 Persistent Storage (Room)
- Save reminders locally  
- Fetch all reminders or active reminders via Flow  
- Re-registers geofences on app restart  

### 🧩 Clean Architecture + Koin Dependency Injection
- Separation of **data**, **domain**, and **presentation**  
- Repository + Use Case pattern  
- Koin for clear dependency wiring  
- ViewModels for each screen  

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| UI | Jetpack Compose, Material3 |
| Map & Location | Mapbox Maps SDK, Mapbox Geofencing, FusedLocation |
| Architecture | MVVM + Clean Architecture |
| DI | Koin |
| Storage | Room Database |
| Background | Foreground Service, Geofence Observer |
| Async | Coroutines + Flow |
| Navigation | Compose Navigation + Kotlin Serialization |



---

## ⚙️ How the App Works

### 1️⃣ User taps on the Map  
→ ViewModel stores selected coordinates  
→ Reverse geocoded using Mapbox Autocomplete  

### 2️⃣ User saves a reminder  
→ Saved via Room  
→ Geofence registered to Mapbox GeofencingService  

### 3️⃣ Foreground Service runs  
→ Continuously receives GPS updates  
→ Mapbox Geofencing triggers events  

### 4️⃣ Entering a geofence  
→ `HandleGeofenceTransitionUseCase` runs  
→ Sends notification  
→ Marks reminder as completed  
→ Removes geofence feature  

---

## 📍 Geofence JSON Example

Each reminder becomes a Mapbox GeoJSON feature:

```json
{
  "type": "Feature",
  "id": "123",
  "properties": {
    "name": "Buy groceries",
    "MBX_GEOFENCE_POINT_RADIUS": 300
  },
  "geometry": {
    "type": "Point",
    "coordinates": [longitude, latitude]
  }
}
```

