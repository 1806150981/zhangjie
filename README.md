# zhangjie
给我张杰小宝贝创建的仓库
package com.icss.demo.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liu jun
 */

public class OpenCsv {

    public static void main(String[] args) {
        System.out.println();
        OpenCsv openCsv = new OpenCsv();
        List<String[]> csv = openCsv.getCsv(",", "E:\\csv\\INPUT.csv");
        List<String[]> outCsv = new ArrayList<>();
        csv.forEach(item -> {
            switch (item[0]) {
                case "A":
                    String[] ss = new String[10];
                    ss[0] = "A";
                    //计算总金额

                    break;
                case "B":
                    break;
                case "C":
                    break;
                default:
                    break;
            }
        });
    }

    public List<String[]> getCsv(String delimiter, String path) {

        ArrayList<String[]> columns = new ArrayList<>();
        // 创建 reader
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                columns.add(line.split(delimiter));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return columns;
    }


    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return
     */
    public File createCSVFile(List<String[]> exportData, String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8), 1024);
            for (String[] exportDatum : exportData) {
                writeRow(exportDatum, csvFileOutputStream);
                csvFileOutputStream.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }


    /**
     * 写一行数据
     *
     * @param row       数据列表
     * @param csvWriter 输入流
     * @throws IOException
     */
    private void writeRow(String[] row, BufferedWriter csvWriter) throws IOException {
        List<String> strings = Arrays.asList(row);

        int i = 0;
        for (String data : strings) {
            csvWriter.write(DelQuota(data));
            if (i != strings.size() - 1) {
                csvWriter.write(",");
            }
            i++;
        }
    }

    /**
     * 剔除特殊字符
     *
     * @param str 数据
     */
    public String DelQuota(String str) {
        String result = str;
        String[] strQuota = {"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":", "/,", "<", ">", "?"};
        for (String s : strQuota) {
            if (result.contains(s)) {
                result = result.replace(s, "");
            }
        }
        return result;
    }


}
