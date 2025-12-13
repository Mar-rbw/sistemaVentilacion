package com.example.sistemaventilacion

/* Notas de investigación sobre android
* UI Layer Pipeline
* El data Layers tiene el proposito de almacenar, administrar y proporcionar acceso a los datos a la app
* El UI Layer debe realizar los siguientes pasos,
* 1. App data -> UI Data
* Convierte datos de la apliacion en datos especificos de la interfaz de usuario
* 2. UI Data -> UI elements
* Actualice los elementos de la interfaz de la ui para reflajar datos especificos de la interfaz de usuario
* 3. User events -> Changes
* Procesar eventos de entrada del usaurio que provocan cambios en la inerfaz de usuario
* 4. Repeat
* y repite todo lo anteior tanto tiempo como sea necesario
*
* UI layer concepts
*1. Define UI state
* 2. Production of UI state
* 3. Expose Ui state
* 4. Consume Ui State
*
*
*Colores del proyecto
* FAD961
* F76B1C
* 42E6D1
* 116B85
* */

/*Estructura JSON*/
//{
//    "activacion": {
//    "$userId": {
//    "MIN_TEM": 20.0,    /* Float */
//    "MAX_TEM": 30.0,    /* Float */
//    "MIN_HUM": 50.0,    /* Float */
//    "MAX_HUM": 80.0,    /* Float */
//    "DURACION": 60,     /* Int (minutos) */
//    "ACTIVADO": true    /* Boolean */
//}
//},
//    "agendar": {
//    "$userId": {
//    "ACTIVADO": true,   /* Boolean */
//    "FECHA": "2025-12-25", /* String */
//    "HORA": "08:30",    /* String */
//    "DURACION": 30      /* Int (minutos) */
//}
//},
//    "control": {
//    "state": true, /* Boolean*/
//    "temperatura": {
//    "TEMP_ON_C": 25.0,  /* Float (ControlTempC) */
//    "TEMP_OFF_C": 24.0  /* Float (ControlTempC) */
//}
//},
//    "historial": {
//    /* Mapea 'History'*/
//    "$userId": {
//    "$pushId1": {
//    "timestamp": 1678886400000, /* Long */
//    "userId": "...",            /* String */
//    "userName": "UsuarioPrueba", /* String */
//    "action": "Temperatura máxima cambiada", /* String */
//    "actionType": "CONFIG_CHANGE", /* Enum (String en DB) */
//    "previousValue": "30.0",    /* String (Nullable) */
//    "newValue": "32.0"          /* String (Nullable) */
//}
//}
//},
//    "monitor": {
//    /* Mapea 'SensorPocket' (datos de sensores) */
//    "sensores": {
//    "sensor1": {
//    "temperatura": 25.5, /* Float */
//    "humedad": 60.2,     /* Float */
//    "timestamp": 1678886400000, /* Long */
//    "counter": 15        /* Int (Nullable) */
//}
//}
//},
//    "notHum": {
//    /* Mapea 'NotificacionHumedad' (por usuario) */
//    "$userId": {
//    "MIN_HUM": 45.0,   /* Float */
//    "MAX_HUM": 75.0    /* Float */
//}
//},
//    "notTem": {
//    /* Mapea 'NotificacionTemperatura' (por usuario) */
//    "$userId": {
//    "MIN_TEM": 18.0,   /* Float */
//    "MAX_TEM": 35.0    /* Float */
//}
//}
//}