package com.xuecheng.manage_media_process;

import com.xuecheng.framework.utils.Mp4VideoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-12 9:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProcessBuilder {

    @Test
    public void testProcessBuilder() throws IOException {
        //创建ProcessBuilder对象
        ProcessBuilder processBuilder = new ProcessBuilder();
        //设置执行的第三方程序(命令)
        List<String> cmds = new ArrayList<>();
        cmds.add("ping");
        cmds.add("127.0.0.1");
        processBuilder.command(cmds);
        //合并标准输入流和错误输出
        processBuilder.redirectErrorStream(true);
        Process start = processBuilder.start();
        //获取输入流
        InputStream inputStream = start.getInputStream();
        //将输入流转换为字符输入流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");

        //获取流的数据
        int len = -1;
        //数据缓冲区
        char[] cache = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = inputStreamReader.read(cache)) != -1) {
            //获取缓冲区内的数据
            String outStr = new String(cache, 0, len);
            System.out.println(outStr);
            stringBuffer.append(outStr);
        }
        inputStream.close();
    }

    //测试使用工具类将avi转成mp4
    @Test
    public void testProcessMp4() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //定义命令内容
        List<String> command = new ArrayList<>();
        command.add("D:/soft/ffmpeg-20200315-c467328-win64-static/bin/ffmpeg.exe");
        command.add("-i");
        command.add("E:/temp/1.avi");
        command.add("-y"); //覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("E:/temp/1.mp4");
        processBuilder.command(command);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        Process start = processBuilder.start();
        InputStream inputStream = start.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream, "gbk");

        //获取输入流数据
        int len = -1;
        //数据缓冲区
        char[] cache = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while ((len=streamReader.read(cache)) != -1){
            //从缓冲区获取数据
            String out = new String(cache, 0, len);
            System.out.println(out);
            stringBuffer.append(out);
        }
        inputStream.close();
    }
}
