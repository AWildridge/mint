require 'socket'
require 'json'

# Batched packet id
batched = 255

# The amount of chunks in the batched packet (up to 32 chunks)
amt_chunks = 1

# Spawn player packet id
spwn_plr = 0

# 30 bit hash location
location = 0

# Response code
response = rand(55555)

packet = [ batched, amt_chunks, 0, spwn_plr, location, response ].pack("CCCCLQ")

socket = UDPSocket.new
socket.send packet, 0, "127.0.0.1", 5555

data = socket.recvfrom(1000)
p JSON.parse(data[0])

packet = [ 6, 1, 0x8001, -1 ].pack("CCL>Q")
socket.send packet, 0, "127.0.0.1", 5555

data = socket.recvfrom(1000)
p JSON.parse(data[0])

packet = [ 0x20 ].pack("C")
socket.send packet, 0, "127.0.0.1", 5555

packet = [ 7, 1, -1 ].pack("CS>Q")
socket.send packet, 0, "127.0.0.1", 5555

data = socket.recvfrom(1000)
p JSON.parse(data[0])