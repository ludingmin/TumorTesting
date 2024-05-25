package com.tumorTest.schedu;

import com.tumorTest.constant.RedisConstant;
import com.tumorTest.mapper.BookingMapper;
import com.tumorTest.properties.PythonProperties;
import com.tumorTest.uitl.AliOssUtil;
import com.tumorTest.uitl.CommonUtil;
import com.tumorTest.uitl.PythonUitl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SendImgSchedu {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private AliOssUtil aliOssUtil;
    //检测是否已经预测好(10分钟一次)
    @Autowired
    private  PythonProperties properties;
    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private PythonUitl pythonUitl;
    @Scheduled(fixedRate = 60000*1)
    public void checkAndSend(){
        log.info("定时任务正在执行");
        //获取正在等待结果的bookid
        Set<Object> set = redisTemplate.opsForSet().members(RedisConstant.KEY_BOOKING_USER);
        Set<String> bookingIds = set.stream().map(Object::toString).collect(Collectors.toSet());
        //根据bookid，查看文件夹下是否生产结果文件
        for (String bookid : bookingIds){
            String liverName = properties.getResultPath()+bookid+"_liver.nii.gz";
            String tumorName = properties.getResultPath()+bookid+"_tumor.nii.gz";
            log.info("liverName:{}",liverName);
            log.info("tumorName:{}",tumorName);
            if (!(new File(liverName).exists() && new File(tumorName).exists()))
                continue;
            if (!pythonUitl.changeImg(Long.valueOf(bookid))) {
                continue;
            }
            //上传文件OSS
            //读取图片路径下的文件
            File floder = new File(properties.getImgPath() + bookid);
            File[] files = floder.listFiles();
            if (files == null)
                return;
            //现在是只上传一张图片;
            for (File file : files){
                log.info("正在上传图片{}",file.getName());
                String imgUrl = aliOssUtil.upload(CommonUtil.readFileToBytes(file),bookid);
                // 根据Url的地址存入数据库
                bookingMapper.updateImgUrlByBookingId(bookid,imgUrl);
                // 跟新预约状态为完成
                bookingMapper.updateState(bookid,1);
                System.out.println(imgUrl);
                break;
            }


        }

    }


}
