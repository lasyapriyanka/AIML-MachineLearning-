import numpy as np
from sklearn.neighbors import NearestNeighbors
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.model_selection import KFold, cross_val_score
import math

def k_fold_value(n):
	error_list=[[],[],[]]
	k_fold = KFold(n_splits=3)
	l=0
	for train_indices, test_indices in k_fold.split(train):
		label_list=[]
		test_label=[]
		train_list=[]
		test_list=[]
		for i in train_indices:
			label_list.append([train_labels[i]])
			train_list.append(train[i])
		for i in test_indices:
			test_label.append([train_labels[i]])
			test_list.append(train[i])
		for i in range(1,n+1):
			out_list=k_nearest(i,train_list,label_list,test_list)
			acc=accuracy(out_list,test_label)
			error_list[l].append(float(100-acc))
		l=l+1
	mean_list=[]
	k=len(error_list[0])
	for i in range(k):
		add=0
		for j in range(3):
			add=add+error_list[j][i]
		add_mean=float(add/k)
		mean_list.append(add_mean)
	m=min(mean_list)
	return (mean_list,error_list,mean_list.index(m)+1)

def variance_std(error_list,mean_list):
	k=len(error_list[0])
	std=[]
	for i in range(k):
		var=0
		for j in range(3):
			var=var+pow(abs(error_list[j][i]-mean_list[i]),2)
		variance=float(var/k)
		standard_deviation=math.sqrt(variance)
		std.append(standard_deviation)
	return std

def file_to_text(filename):
	occupancy=[]
	data_list=[]
	labels=[]
	file=open(filename)
	for line in file:
		if line[1].isalpha()==False:
			line=line.split(',')
			for i in line:
				i = i[1:-1] #Remove starting and ending quotes.
			line[7]=line[7][:-1] #Remove '\n'
			line[7]=int(line[7])
			lst=[]
			line[2]=float(line[2])
			line[2]=float(line[3])
			line[2]=float(line[4])
			line[2]=float(line[5])
			lst.append(line[2])
			lst.append(line[3]) #2nd
			lst.append(line[4]) #3rd
			lst.append(line[5]) #4th
			occupancy.append(line[7]) #5th or class label.
			data_list.append(lst)
	labels=occupancy
	return (data_list,labels)

def k_nearest(k,trains,train_label,test1):
	neigh = KNeighborsClassifier(n_neighbors=k,metric='euclidean')
	neigh.fit(trains,train_label)
	result1=neigh.predict(test1)
	return result1

def k_nearest_weighted(k,train,train_labels,test1):
	neigh = KNeighborsClassifier(n_neighbors=k,metric='euclidean',weights='distance')
	neigh.fit(train,train_labels)
	result1=neigh.predict(test1)
	return result1

def positive(a,b):
	if a==b:
		return 1
	else:
		return 0
#Check a result list with actual output.
def accuracy(result,actual):
	tp=0
	if len(result)!=len(actual):
		print "Comparing two different sets."
		return
	for i in range(len(result)):
		if result[i]==actual[i]:
			tp=tp+1
	k=float(len(result))
	accuracy=float(tp*100/k)
	return accuracy

## Get data from file.
(train,train_labels)=file_to_text('Datasets/datatraining.txt')
(test1,test1_labels)=file_to_text('Datasets/datatest.txt')
(test2,test2_labels)=file_to_text('Datasets/datatest2.txt')
n=input("Enter maximum value of k to test:")
(mean_list,error_list,k)=k_fold_value(n)
std=variance_std(error_list,mean_list)
print "Using 3-fold validation, the value of k is %d"%(k)
print "Giving it as an input to K Nearest Neighbors classifier:-"

k_near_set1=k_nearest(k,train,train_labels,test1)
k_near_set2=k_nearest(k,train,train_labels,test2)
k_near_w1=k_nearest_weighted(k,train,train_labels,test1)
k_near_w2=k_nearest_weighted(k,train,train_labels,test2)

acc1=accuracy(k_near_set1,test1_labels)
acc2=accuracy(k_near_set2,test2_labels)
acc3=accuracy(k_near_w1,test1_labels)
acc4=accuracy(k_near_w2,test2_labels)


print "Accuracy of testing data 1 on k-neighbours classification is",acc1,"%"
print "Accuracy of testing data 2 on k-neighbours classification is",acc2,"%"
print "Accuracy of testing data 1 on weighted k-neighbours classification is",acc3, "%"
print "Accuracy of testing data 1 on weighted k-neighbours classification is",acc4,"%"
