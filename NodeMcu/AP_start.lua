ap_config={
	ssid="Home_automation_check",
	pwd="connectMe"
}
--wifi.setmode(wifi.AP)
wifi.ap.config(ap_config)
print(wifi.ap.getip())
