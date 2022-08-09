package com.icss.demo.bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liu jun
 */

public class Main {
    public static void main(String[] args) {

        //定义文件路径
        File file = new File("E:\\新建文件夹\\CC.txt");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                System.err.println(e);
            }

        }

//        fileWriter.write("张杰NB");
//        fileWriter.close();


    }
}
