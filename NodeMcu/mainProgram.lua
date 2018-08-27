server=net.createServer(net.TCP)

function performOperation(device,op)
	if (op == "ON") then
		gpio.write(device,gpio.HIGH)
        status = "ON"

	elseif(op == "OFF") then
		gpio.write(device,gpio.LOW)
		status = "OFF"
	end
    retString  = "Dev,"..device..",Turned,"..status
    print(retString)
    return retString
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
		retString = performOperation(tonumber(device),op)
		sck:send(retString)
	end
	sck:on("sent",function()  sck:close() end )
end

if server then
	server:listen(40000,function(conn)
		--conn:send("Hello there, I am Home Automation Server")
        port,ip = conn:getpeer()
        print("Got connection from "..ip.." "..port)
		conn:on("receive", receiver)
		end)
end
