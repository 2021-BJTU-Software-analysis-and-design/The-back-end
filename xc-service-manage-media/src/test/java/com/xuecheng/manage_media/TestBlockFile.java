package com.xuecheng.manage_media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBlockFile {

    /**
     * 测试文件分块
     */
    @Test
    public void testChunk() throws IOException {
        String chunkPath = "E:/Project/XueChengOnline/xcEduUI01/xuecheng/video/";
        String fileName = "lucene.mp4";
        File sourceFile = new File(chunkPath + fileName);
        File chunkFolder = new File(chunkPath + fileName + ".chunk");
        if(!chunkFolder.exists()){
            chunkFolder.mkdir();
        }

        //分块大小
        long chunkSize = 1024*1024*1;
        //分块数量
        //Math.ceil向上取整,例如 12.1=13,12.8=13
        long chunkNum = (long) Math.ceil((sourceFile.length() * 1.0) / chunkSize);
        chunkNum = chunkNum <= 0 ? 1 : chunkNum;
        //缓冲区大小
        byte[] byte_cache = new byte[1024];
        //使用RandomAccessFile访问文件
        RandomAccessFile rafRead = new RandomAccessFile(sourceFile, "r");
        //分块
        for (int i = 0; i < chunkNum; i++) {
            //创建分块文件
            File chunkFile = new File(chunkFolder.getPath() + "/" + i);
            boolean newFile = chunkFile.createNewFile();
            if (newFile){
                //向分块文件中写入数据
                RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
                int len = -1;
                //读取到-1则表示读取完成
                while ((len = rafRead.read(byte_cache)) != -1){
                    raf_write.write(byte_cache,0, len);
                    //读取到预期块大小时结束
                    if (chunkFile.length() >= chunkSize){
                        break;
                    }
                }
                raf_write.close();
            }
        }
        rafRead.close();
    }

    /**
     * 测试块合并
     */
    @Test
    public void testMerge() throws IOException {
        //块文件目录
        File chunkFolder = new File("E:/Project/XueChengOnline/xcEduUI01/xuecheng/video/lucene.mp4.chunk");
        //合并文件
        File mergeFile = new File("E:/Project/XueChengOnline/xcEduUI01/xuecheng/video/lucene1.mp4");
        if(mergeFile.exists()){
            mergeFile.delete();
        }
        //创建新的合并文件
        boolean newFile = mergeFile.createNewFile();
        //用于写文件
        RandomAccessFile rafWrite = new RandomAccessFile(mergeFile, "rw");
        //指针指向文件顶端
        rafWrite.seek(0);
        //缓冲区
        byte[] byte_cache = new byte[1024];
        //分块文件列表
        File[] fileArray = chunkFolder.listFiles();
        //将分块列表转为集合,便于排序
        ArrayList<File> fileList = new ArrayList<>(Arrays.asList(fileArray));
        //从小到大排序,按名称升序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                //比较两个文件的名称
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                return 1;
            }
        });

        //合并文件
        for (File chunkFile: fileList) {
            RandomAccessFile rafRead = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            //循环读取并写入数据
            while ((len = rafRead.read(byte_cache)) != -1){
                rafWrite.write(byte_cache,0, len);
            }
            rafRead.close();
        }
        rafWrite.close();
    }
}
