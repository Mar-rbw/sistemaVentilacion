#include <ESP8266WiFi.h>
#include <espnow.h>
#include <FirebaseESP8266.h>

const char* ssid = "EVELIN";
const char* password = "21232063";

#define FIREBASE_HOST "iot-proyecto-225f7-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "ei7g3jbFjbelc6qwrApW4q5kEDK0zTSPzo24DDAu"

FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

#define RELAY_PIN D6
#define LED_VERDE D5
#define LED_ROJO  D8

// ESP-NOW ESTRUCTURA
typedef struct struct_message {
  float temp;
  float hum;
} struct_message;

//VARIABLES GLOBALES
volatile bool newData = false;
float tempRX = 0.0;
float humRX  = 0.0;
unsigned long lastFirebaseSend = 0;

//CALLBACK ESP-NOW
void OnDataRecv(uint8_t *mac, uint8_t *incomingDataRaw, uint8_t len) {
  struct_message data;
  memcpy(&data, incomingDataRaw, sizeof(data));

  tempRX = data.temp;
  humRX  = data.hum;
  newData = true;

  Serial.print("RECIBIDO -> Temp: ");
  Serial.print(tempRX);
  Serial.print(" °C | Hum: ");
  Serial.print(humRX);
  Serial.println(" %");
}


void setup() {
  Serial.begin(9600);
  delay(100);

  // ---- WiFi ----
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  Serial.print("Conectando WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi conectado");
  Serial.print("Canal WiFi: ");
  Serial.println(WiFi.channel());

  //Firebase
  config.database_url = "iot-proyecto-225f7-default-rtdb.firebaseio.com";
  config.signer.test_mode = true;
  auth.user.email = "";
  auth.user.password = "";

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
  delay(2000);


  pinMode(RELAY_PIN, OUTPUT);
  pinMode(LED_VERDE, OUTPUT);
  pinMode(LED_ROJO, OUTPUT);

  digitalWrite(RELAY_PIN, LOW);
  digitalWrite(LED_VERDE, LOW);
  digitalWrite(LED_ROJO, HIGH);

  //ESP-NOW
  if (esp_now_init() != 0) {
    Serial.println("Error inicializando ESP-NOW");
    return;
  }

  esp_now_set_self_role(ESP_NOW_ROLE_SLAVE);
  esp_now_register_recv_cb(OnDataRecv);

  Serial.println("ESP-NOW listo");
}

void loop() {

  //Envia datos a Firebase cada 3 segundos
  if (newData && millis() - lastFirebaseSend > 3000) {

    if (Firebase.ready()) {
      Firebase.setFloat(fbdo, "/monitor/sensores/sensor1/temperatura", tempRX);
      Firebase.setFloat(fbdo, "/monitor/sensores/sensor1/humedad", humRX);
      Firebase.setInt(fbdo, "/monitor/sensores/sensor1/timestamp", millis());

      Serial.println("Firebase actualizado");
    } else {
      Serial.println("Firebase NO listo");
    }

    lastFirebaseSend = millis();
    newData = false;
  }

  //Control desde la app
  if (Firebase.getBool(fbdo, "/control/state")) {
    bool estado = fbdo.boolData();
    digitalWrite(RELAY_PIN, estado ? HIGH : LOW);
  }

  delay(10); //Para no saturar CPU
}
