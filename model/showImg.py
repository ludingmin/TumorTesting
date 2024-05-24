# 分割结果展示
#%matplotlib inline
import sys
if './external-libraries' not in sys.path:
    sys.path.append('./external-libraries')
import numpy as np 
import matplotlib.pyplot as plt
import skimage.color
import nibabel as nib 
import os

scan_file = nib.load('C://Users/DELL/Desktop/model/scan/volume.nii.gz')
scan = scan_file.get_fdata()
scan = (scan + 300) / 600 * 255  # 类似于窗口化操作，让CT看得更清楚
scan = scan.clip(0, 255)

liver_seg = nib.load('C://Users/DELL/Desktop/model/seg/liver_seg.nii.gz').get_fdata().astype('uint8')
tumor_seg = nib.load('C://Users/DELL/Desktop/model/seg/tumor_seg.nii.gz').get_fdata().astype('uint8')

tumor_seg[liver_seg == 0] = 0  # 肝脏肿瘤都在肝脏内部，去掉肝脏外部的false positive

for slice_ind in range(60, 65): # 只展示了其中的 5 张，调整range可以看到其他的片子
    img = skimage.color.gray2rgb(scan[:, :, slice_ind]).astype('uint8')
    
    liver_seg_slice = liver_seg[:, :, slice_ind]
    img[liver_seg_slice == 1, :] += np.array([30, 0, 0], dtype='uint8') # 将分割结果中是肝的位置蒙上一层浅红色

    tumor_seg_slice = tumor_seg[:, :, slice_ind]
    img[tumor_seg_slice == 1, :] += np.array([0, 30, 0], dtype='uint8') # 将分割结果中是肝的位置蒙上一层浅红色
    
    output_folder = "C://Users/DELL/Desktop/model/outimg/"
    # plt.figure(figsize=(10,10))
    plt.imshow(img)
    plt.axis('off')  # 关闭坐标轴
    plt.savefig(output_folder+str(slice_ind), bbox_inches='tight', pad_inches=0)  # 保存图像
    print("正在导出图片")
    # plt.show()
    # plt.close()