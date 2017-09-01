//#include <SoftwareSerial.h> 

const int pinOut = 8;
const int pin_rx = 1;
const int pin_tx = 0;
//SoftwareSerial Bluetooth(pin_rx, pin_tx);
int bluetoothData;


void setup() {
  Serial.begin(9600); // initialize serial communication at 9600 bits per second:
//  Bluetooth.begin(9600);

}

void loop() {

  if (Serial.available()){
    bluetoothData = Serial.read();
    Serial.println(bluetoothData);
  }
  
}
