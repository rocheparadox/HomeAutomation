server=net.createServer(net.TCP)
print("Into main program")
function getPins()
  if file.exists("devicePins.txt") then
    pinsFile = file.open("devicePins.txt")
    pins = sjson.decode(pinsFile.read())
    pinsFile.close()
  end
return pins
end

function performOperation(device,op)
  print("To perform operation "..device..op)
  OFF = gpio.LOW
  ON  = gpio.HIGH
  pins = getPins()
  for k,v in pairs(pins) do
    print(k,pins[k])
  end
  devicePin = pins[device]
  print(devicePin)
	if (op == "ON") then
		gpio.write(devicePin,gpio.HIGH)
        status = "ON"

	elseif(op == "OFF") then
		gpio.write(devicePin,gpio.LOW)
		status = "OFF"
	end
  retString  = "Dev,"..device..",Turned,"..status
  print(retString)
  return retString
end

function changeStatusInFile(device,op)

    if file.exists("devicesCurrentStatus.txt") then
      currentStatusFile = file.open("devicesCurrentStatus.txt")
      fileString = currentStatusFile.read()
      deviceStatusTable = sjson.decode(fileString)
      deviceStatusTable[device] = op

      fileString = sjson.encode(deviceStatusTable)
      print(fileString)
      currentStatusFile.close()
      currentStatusFile = file.open("devicesCurrentStatus.txt","w")
      currentStatusFile.write(fileString)
      currentStatusFile.close()
    end
end

function receiver(sck,data)
    --print(sck)
	print("raw Data"..data)
	if(data == "introduce?") then
        print("Asked to introduce..")
		sck:send("IamServer")
	elseif(string.find(data,"DO")) then --Protocol Do,deviceNumber,operation'

		temp,device, op = data:match("([^,]+),([^,]+),([^,]+)")
		print(device.." "..op)
		retString = performOperation(device,op)
        changeStatusInFile(device,op)
		sck:send(retString)
	end
	sck:on("sent",function()  sck:close() end )
end


if server then
    print("Listens in port 40000")
	server:listen(40000,function(conn)
		--conn:send("Hello there, I am Home Automation Server")
        port,ip = conn:getpeer()
        print("Got connection from "..ip.." "..port)
		conn:on("receive", receiver)
		end)
else
    print("Server not created")
end
