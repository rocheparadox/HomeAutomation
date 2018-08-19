server=net.createServer(net.TCP)

function performOperation(device,op)
	if (op == "ON") then
		gpio.write(device,gpio.HIGH)
		print("Dev,"..device..",Turned"..",ON")
	elseif(op == "OFF") then
		gpio.write(device,gpio.LOW)
		print("Dev,"..device..",Turned"..",OFF")
	end
end

function receiver(sck,data)
	--print(sck)
	print(data,1,2)
	if(data == "introduce?") then
        print("Asked to introduce..")
		sck:send("IamServer")
	elseif(string.find(data,"Do")) then --Protocol Do,deviceNumber,operation'

		temp,device, op = data:match("([^,]+),([^,]+),([^,]+)")
		print(device.." "..op)
		performOperation(tonumber(device),op)
		sck:send("success")
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
