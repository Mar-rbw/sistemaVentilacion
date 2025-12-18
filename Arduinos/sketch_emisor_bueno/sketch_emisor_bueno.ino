#include <ESP8266WiFi.h>
#include <espnow.h>
#include "DHT.h"

#define DHTPIN D2        
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

//Dirección MAC del receptor
uint8_t broadcastAddress[] = {0x68, 0xC6, 0x3A, 0xF3, 0x68, 0x8D};

typedef struct struct_message {
  float temp;
  float hum;
} struct_message;

struct_message myData;

// Función callback al enviar
void OnDataSent(uint8_t *mac_addr, uint8_t sendStatus) {
  Serial.print("Estado del envío: ");
  if (sendStatus == 0) {
    Serial.println("Éxito ");
  } else {
    Serial.println("Fallo ");
  }
}

void setup() {
  Serial.begin(9600);
  WiFi.mode(WIFI_STA);
  dht.begin();

  if (esp_now_init() != 0) {
    Serial.println("Error inicializando ESP-NOW");
    return;
  }

  esp_now_set_self_role(ESP_NOW_ROLE_CONTROLLER);
  esp_now_register_send_cb(OnDataSent);
  esp_now_add_peer(broadcastAddress, ESP_NOW_ROLE_SLAVE, 1, NULL, 0);
}

void loop() {
  float t = dht.readTemperature();
  float h = dht.readHumidity();

  if (isnan(t) || isnan(h)) {
    Serial.println("Error leyendo DHT11");
    delay(2000);
    return;
  }

  myData.temp = t;
  myData.hum = h;

  esp_now_send(broadcastAddress, (uint8_t *)&myData, sizeof(myData));

  Serial.print("Temperatura enviada: ");
  Serial.print(t);
  Serial.print(" °C | Humedad: ");
  Serial.print(h);
  Serial.println(" %");

  delay(3000);  // cada 3s
}
