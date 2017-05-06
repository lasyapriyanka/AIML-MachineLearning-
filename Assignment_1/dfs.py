def move_right(node):
	i = node.index(0)
	if i  in [2,5,8]:
		return -1
	temp = node[i+1]
	node[i+1] = node[i]
	node[i] = temp 
	return node


def move_up(node):
	i = node.index(0)
	if i in [0,1,2]:
		return -1
	temp = node[i]
	node[i] = node[i-3]
	node[i-3] = temp
	return node

def move_left(node):
        i = node.index(0)
        if i  in [0,3,6]:
                return -1
        temp = node[i-1]
        node[i-1] = node[i]
        node[i] = temp
        return node


def move_down(node):
	i = node.index(0)
	if i in [6,7,8]:
		return -1
	temp = node[i]
	node[i] = node[i+3]
	node[i+3] = temp
	return node




def dfs(s,e):
	visited = []  
	sri = [(s,[])] 
	while sri:
		current = tuple(sri.pop())
		current[1].append(current[0])
		if(current[0] == e):
			return current[1]
			
		if (move_left(list(current[0])) != -1) and (current[0] not in visited):
			t = tuple((move_left(list(current[0])),list(current[1])))
			sri.append(t)
		if (move_up(list(current[0])) != -1) and (current[0] not in visited):
			t = tuple((move_up(list(current[0])),list(current[1])))
			sri.append(t)
		if (move_down(list(current[0])) != -1) and (current[0] not in visited):
			t = tuple((move_down(list(current[0])),list(current[1])))
			sri.append(t)
		if (move_right(list(current[0])) != -1) and (current[0] not in visited):
			t = tuple((move_right(list(current[0])),list(current[1])))
			sri.append(t)
		if current[0] not in visited:
			visited.append(current[0])








start = []
final = []
cnt =0
print "Enter the initial state (one num per line)"
for i in range(0,9):
	start.append(int(raw_input()))

print "Enter the final state"
for i in range(0,9):
	final.append(int(raw_input()))
path =  dfs(list(start),list(final))
for node in path:
	cnt=cnt+1
	print node[0:3]
	print node[3:6]
	print node[6:9]
	print 
	print

print cnt
