const int pinOut = 8;
const int pin_rx = 12;
const int pin_tx = 13;


void setup() {
  Serial.begin(9600); // initialize serial communication at 9600 bits per second:
 
  pinMode(pin_rx, INPUT);

}

void loop() {
  int rx_data = digitalRead(pin_rx);

  Serial.println(rx_data);
  
}
