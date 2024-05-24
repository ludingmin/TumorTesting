import sys
import numpy as np
import math
from scipy import ndimage
import os 

def listdir(path):
    '''返回目录下所有的文件，去掉一些常见的特殊目录，并通过相同的字典序保证两个文件名序列一一对应
    path: 目录
    '''
    files=os.listdir(path)
    if ".DS_Store" in files:
        files.remove(".DS_Store")
    if "checkpoint" in files:
        files.remove("checkpoint")
    files.sort()
    return files

def clip_label(label, category):
    '''针对有多类标签的情况，将category和比category大的类别赋为1,其余的赋为0
    label: 分割的标签
    category: 保留的类别
    '''
    label[label < category] = 0
    label[label >= category] = 1
    return label

def filter_largest_volume(seg):
    '''只保留分割标签中最大的连通块
    seg: numpy数组
    '''
    # TODO 需要能处理vol 是一个batch的情况
    seg, num = ndimage.label(seg, np.ones([3,3,3]))
    maxi = 0
    maxnum = 0
    for i in range(1,num):
        count = seg[seg == i].size
        if count > maxnum:
            maxi = i
            maxnum = count
    seg[seg != maxi ] = 0
    seg[seg == maxi] = 1
    return seg




