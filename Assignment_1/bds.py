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

def bfs(s,e):
	visited_init = []
	visited_final =[]
	start = []
	final = []
	sri_init = [(s,[])] 
	sri_final = [(e,[])]
	while ( sri_final or sri_init ):
		
		
		status_init=0
		status_final=0

		if(sri_init):
			status_init=1
			curr_init = tuple(sri_init.pop(0))
			curr_init[1].append(curr_init[0])
	
		if(sri_final):
			status_final=1
			curr_final = tuple(sri_final.pop(0))
			curr_final[1].append(curr_final[0])



		j=0
		for i in visited_init:

			if (i in visited_final):
			
				
				temp=[]
				for k in start[j][1]:
					temp.append(k)
				for k in final[j][1][::-1]:
					temp.append(k)
				return  temp
			j=j+1
		
			
		if(status_init):
		#	print "entered if_init"
			if (move_left(list(curr_init[0])) != -1) and (curr_init[0] not in visited_init):
				t = tuple((move_left(list(curr_init[0])),list(curr_init[1])))
				sri_init.append(t)
			if (move_right(list(curr_init[0])) != -1) and (curr_init[0] not in visited_init):
				t = tuple((move_right(list(curr_init[0])),list(curr_init[1])))
				sri_init.append(t)
			if (move_up(list(curr_init[0])) != -1) and (curr_init[0] not in visited_init):
				t = tuple((move_up(list(curr_init[0])),list(curr_init[1])))
				sri_init.append(t)
			if (move_down(list(curr_init[0])) != -1) and (curr_init[0] not in visited_init):
				t = tuple((move_down(list(curr_init[0])),list(curr_init[1])))
				sri_init.append(t)
			if (curr_init[0] not in visited_init):
				visited_init.append(curr_init[0])
				start.append(curr_init)

		
		if(status_final):	
		#       print "entered if_final
			
			if (move_left(list(curr_final[0])) != -1) and (curr_final[0] not in visited_final):
				t = tuple((move_left(list(curr_final[0])),list(curr_final[1])))
				sri_final.append(t)
			if (move_right(list(curr_final[0])) != -1) and (curr_final[0] not in visited_final):
				t = tuple((move_right(list(curr_final[0])),list(curr_final[1])))
				sri_final.append(t)
			if (move_up(list(curr_final[0])) != -1) and (curr_final[0] not in visited_final):
				t = tuple((move_up(list(curr_final[0])),list(curr_final[1])))
				sri_final.append(t)
			if (move_down(list(curr_final[0])) != -1) and (curr_final[0] not in visited_final):
				t = tuple((move_down(list(curr_final[0])),list(curr_final[1])))
				sri_final.append(t)
			if (curr_final[0] not in visited_final):
				visited_final.append(curr_final[0])
				final.append(curr_final)




start = []
final = []
cnt =0
print "Enter the initial state (one num per line)"
for i in range(0,9):
	start.append(int(raw_input()))

print "Enter the final state"
for i in range(0,9):
	final.append(int(raw_input()))
path =  bfs(list(start),list(final))

for node in path:
	cnt=cnt+1
	print node[0:3]
	print node[3:6]
	print node[6:9]
	print 
	print

print cnt
