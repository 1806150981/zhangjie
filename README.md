# zhangjie
给我张杰小宝贝创建的仓库


···
package com.icss.demo.service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author liu jun
 */

public class OpenCsv {

    private final Integer D_POSITION = 3;
    private final Integer E_POSITION = 4;
    private final Integer F_POSITION = 5;
    private final String OUTPUT_PATH = "E:\\csv";

    public static void main(String[] args) {
        System.out.println();
        OpenCsv openCsv = new OpenCsv();
        List<String[]> csv = openCsv.getCsv(",", "E:\\csv\\INPUT.csv");
        List<String[]> outCsv = new ArrayList<>();
        //输出系统时间
        String[] ssT = new String[2];
        ssT[0] = "H";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ssT[1] = simpleDateFormat.format(date);
        outCsv.add(ssT);
        //A金额
        BigDecimal totalMoneyA = new BigDecimal(0);
        //B金额
        BigDecimal totalMoneyB = new BigDecimal(0);
        //C金额
        BigDecimal totalMoneyC = new BigDecimal(0);

        for (String[] item : csv) {
            if ("A".equals(item[0])) {
                //计算总金额 从DEF开始相加 使用bigdecimal
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //E
                BigDecimal moneyE = new BigDecimal(item[openCsv.E_POSITION]);
                //F
                BigDecimal moneyF = new BigDecimal(item[openCsv.F_POSITION]);
                //总金额
                totalMoneyA = totalMoneyA.add(moneyD).add(moneyE).add(moneyF);

            } else if ("B".equals(item[0])) {
                //计算总金额 从DEF开始相加 使用bigdecimal
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //E
                BigDecimal moneyE = new BigDecimal(item[openCsv.E_POSITION]);
                //总金额
                totalMoneyB = totalMoneyB.add(moneyD).add(moneyE);
            } else if ("C".equals(item[0])) {
                //计算总金额 从DEF开始相加 使用bigdecimal
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //总金额
                totalMoneyC = totalMoneyC.add(moneyD);
            }
        }

        //录入A
        String[] ssA = new String[2];
        ssA[0] = "A";
        ssA[1] = totalMoneyA.toString();
        outCsv.add(ssA);
        //录入B
        String[] ssB = new String[2];
        ssB[0] = "B";
        ssB[1] = totalMoneyB.toString();
        outCsv.add(ssB);
        //录入C
        String[] ssC = new String[2];
        ssC[0] = "C";
        ssC[1] = totalMoneyC.toString();
        outCsv.add(ssC);


        //输入总金额
        String[] ssL = new String[2];
        ssL[0] = "L";
        StringBuilder totalMoney = new StringBuilder(totalMoneyA.add(totalMoneyB).add(totalMoneyC).toString());
        if (totalMoney.length() < 11) {
            for (int i = 0; i <= (11 - totalMoney.length()); i++) {
                totalMoney.insert(0, "0");
            }
        }
        totalMoney.insert(0, "`");
        totalMoney.append("`");
        ssL[1] = totalMoney.toString();
        outCsv.add(ssL);

        //输出文件
        openCsv.createCSVFile(outCsv, openCsv.OUTPUT_PATH, "OUTPUT");
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
            if (result.indexOf(s) < 0) {
                result = result.replace(s, "");
            }
        }
        return result;
    }


}

···
