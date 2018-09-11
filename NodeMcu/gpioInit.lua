for pin=0,4 do
  print(pin)
  gpio.mode(pin,gpio.OUTPUT)
end

devicePins = {
  hall_light=1,
  hall_fan=2,
  portico_light=3,
  portico_fan=4
}

OFF = gpio.HIGH
ON  = gpio.LOW

if file.exists("devicesCurrentStatus.txt") then
  currentStatusFile = file.open("devicesCurrentStatus.txt")
  fileString = currentStatusFile.read()
  print(fileString)
  deviceStatusTable = sjson.decode(fileString)

  for key,value in pairs(deviceStatusTable) do
    device = devicePins[key]
    op = value
  if (op == "ON") then
    gpio.write(device,gpio.HIGH)

  elseif(op == "OFF") then
    gpio.write(device,gpio.LOW)

  end
  end
  currentStatusFile.close()
end
