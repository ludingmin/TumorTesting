package com.tumorTest.uitl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PythonUitl {

    private String startPath;

    public  boolean changeImg(Long orderId){
        try {
            // 创建 ProcessBuilder 对象，指定要执行的命令（Python 解释器路径和要执行的 Python 文件路径）
            ProcessBuilder pb = new ProcessBuilder("python",
                    startPath,orderId.toString());
            // 启动进程
            Process process = pb.start();
            // 获取进程的输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // 读取并打印 Python 脚本的输出
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = stderr.readLine()) != null)
            {
                System.out.println(line);
            }
            // 等待进程执行完成
            int exitCode = process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
