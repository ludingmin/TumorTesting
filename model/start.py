import sys
if './external-libraries' not in sys.path:
    sys.path.append('./external-libraries')
#import paddle.fluid as fluid
# from paddle import fluid
#import paddle as fluid
#
from paddle import fluid
# import paddle.fluid as fluid
import paddle
from util import filter_largest_volume # 之保留标签中最大的连通块，去掉一部分false positive
from tqdm import tqdm
import numpy as np 
import nibabel as nib # 用这个包来读取 nii 格式的CT文件




seg_type = '肿瘤' # 或者肿瘤
assert seg_type in ['肝脏', '肿瘤'], "seg_type只能为肝脏或肿瘤"

if seg_type == '肝脏':
    infer_param_path = "C://Users/DELL/Desktop/model/param/liver_inf" # 肝脏分割权重
    segmentation_path = "C://Users/DELL/Desktop/model/seg/liver_seg.nii.gz"
else:
    infer_param_path = "C://Users/DELL/Desktop/model/param/tumor_inf" # 肿瘤分割权重
    segmentation_path = "C://Users/DELL/Desktop/model/seg/tumor_seg.nii.gz"

# 读取需要分割的CT数据，存入 numpy 数组
scan_file = nib.load('C://Users/DELL/Desktop/model/scan/volume.nii.gz')
scan = scan_file.get_fdata().astype('float32')
scan = scan.clip(-1024, 1024)
print('CT size is ： ', scan.shape)

# 构建一个和输入CT大小相同的空数组，用来存储分割结果
segmentation = np.zeros(scan.shape)

use_cuda = False  # NOTE: 如果运行的不是 paddlepaddle-gpu 版本这里记得改成 False
place = fluid.CUDAPlace(0) if use_cuda else fluid.CPUPlace()
infer_exe = fluid.Executor(place)
inference_scope = fluid.core.Scope()

paddle.enable_static()


with fluid.scope_guard(inference_scope):
        [inference_program, feed_target_names, fetch_targets] = fluid.io.load_inference_model(infer_param_path, infer_exe) # 读取预训练权重
        for slice_ind in range(1, scan.shape[2]-1):
            print('END')     
            scan_slice = scan[:, :, slice_ind - 1: slice_ind + 2] # 2.5D的输入，每次取出CT中3个相邻的层作为模型输入
            scan_slice = scan_slice[np.newaxis, :, :, :] # 添加batch_size维度
            scan_slice = scan_slice.swapaxes(1,3) # 模型的输入是 CWH 的， 通道在第一个维度，因此需要将数组中的第一和第三个维度互换
            result = infer_exe.run(inference_program, feed={feed_target_names[0]: scan_slice }, fetch_list=fetch_targets)
            result = result[0][0][1].reshape([scan.shape[0], scan.shape[1]])
            segmentation[:, :, slice_ind] = result.swapaxes(0,1) # 保存分割结果

print('END')            
segmentation[segmentation >= 0.9] = 1
segmentation[segmentation < 0.9 ] = 0
if seg_type == '肝脏':
    segmentation = filter_largest_volume(segmentation)



seg_file = nib.Nifti1Image(segmentation, scan_file.affine)
nib.save(seg_file, segmentation_path)
print("{}分割完成".format(seg_type))