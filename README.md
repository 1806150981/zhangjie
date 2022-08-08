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


    /**
     * D列的位置
     */
    private final Integer D_POSITION = 3;
    /**
     * E列的位置
     */
    private final Integer E_POSITION = 4;
    /**
     * F列的位置
     */
    private final Integer F_POSITION = 5;
    /**
     * 输出文件位置
     */
    private final String OUTPUT_PATH = "E:\\csv";

    /**
     * 输入文件位置
     */
    public static final String CSV_INPUT_CSV = "E:\\csv\\INPUT.csv";

    public static void main(String[] args) {
        //实例化类 用于调用 OpenCsv 中的方法
        OpenCsv openCsv = new OpenCsv();
        //调用OpenCsv中的方法 得到CSV中所有的数据 数据格式为[ ["A","ID","value"],["B","ID","value"] ]
        List<String[]> csv = openCsv.getCsv(",", CSV_INPUT_CSV);
        //新建容器为了存放 计算后的数据
        List<String[]> outCsv = new ArrayList<>();

        /**
         * 在csv中写入系统时间
         * */
        //创建数据格式 [ ["H","TIME"]]
        String[] ssT = new String[2];
        //第一列的内容
        ssT[0] = "H";
        //得到系统时间
        Date date = new Date();
        //定义格式化系统时间的格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //格式化系统时间
        ssT[1] = simpleDateFormat.format(date);
        //添加到要输出的容器中
        outCsv.add(ssT);
        /**
         * 写入结束
         * */

        //定义容器存储A金额的总和 使用BIGDECIMAL 可以精确计算金额 A金额
        BigDecimal totalMoneyA = new BigDecimal(0);
        //定义容器存储A金额的总和 使用BIGDECIMAL 可以精确计算金额 B金额
        BigDecimal totalMoneyB = new BigDecimal(0);
        //定义容器存储A金额的总和 使用BIGDECIMAL 可以精确计算金额 C金额
        BigDecimal totalMoneyC = new BigDecimal(0);
        //遍历input CSV 中的所有数据 
        for (String[] item : csv) {
            //当数据为A类数据时
            if ("A".equals(item[0])) {
                //item[0]为 A item[2]为id item[3]为D列 以此类推
                //计算总金额 从DEF开始相加
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //E
                BigDecimal moneyE = new BigDecimal(item[openCsv.E_POSITION]);
                //F
                BigDecimal moneyF = new BigDecimal(item[openCsv.F_POSITION]);
                //总金额
                totalMoneyA = totalMoneyA.add(moneyD).add(moneyE).add(moneyF);
                //当数据为B类数据时
            } else if ("B".equals(item[0])) {

                //计算总金额 从DEF开始相加 使用bigdecimal
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //E
                BigDecimal moneyE = new BigDecimal(item[openCsv.E_POSITION]);
                //总金额
                totalMoneyB = totalMoneyB.add(moneyD).add(moneyE);
                //当数据为C类数据时
            } else if ("C".equals(item[0])) {
                //计算总金额 从DEF开始相加 使用bigdecimal
                //D
                BigDecimal moneyD = new BigDecimal(item[openCsv.D_POSITION]);
                //总金额
                totalMoneyC = totalMoneyC.add(moneyD);
            }
        }

        /**
         * 录入第二行 A类的金额总和
         * */
        //录入A
        String[] ssA = new String[2];
        //A 的第一个名称
        ssA[0] = "A";
        //A的值
        ssA[1] = totalMoneyA.toString();
        //添加到输出容器 一会儿统一输出到文件
        outCsv.add(ssA);
        /**
         * 录入第二行结束
         * */

        /**
         * 录入第三行 B类的金额总和
         * */
        //录入B
        String[] ssB = new String[2];
        ssB[0] = "B";
        ssB[1] = totalMoneyB.toString();
        outCsv.add(ssB);
        /**
         * 录入第三行结束
         * */

        /**
         * 录入第四行 C类的金额总和
         * */
        //录入C
        String[] ssC = new String[2];
        ssC[0] = "C";
        ssC[1] = totalMoneyC.toString();
        outCsv.add(ssC);
        /**
         * 录入第四行结束
         * */

        /**
         * 录入第五行
         * */
        //输入总金额
        String[] ssL = new String[2];
        ssL[0] = "L";
        //用StringBuilder去拼接字符串 进行补0操作 
        //先将总金额加到一起
        StringBuilder totalMoney = new StringBuilder(totalMoneyA.add(totalMoneyB).add(totalMoneyC).toString());
        //如果其小于9未 开始补0
        if (totalMoney.length() < 9) {
            //得到补0的个数
            int total = 9 - totalMoney.length();
            for (int i = 0; i < total; i++) {
                //在字符串的开头加上0
                totalMoney.insert(0, "0");
            }
        }
        ssL[1] = totalMoney.toString();
        outCsv.add(ssL);
        /**
         * 录入第五行结束
         * */

        /**
         * 录入第六行 INPUT文件的数量
         * */
        String[] ssL1 = new String[2];
        ssL1[0] = "L1";
        //最开始读取的INPUT文件集合 大小即为 数量
        ssL1[1] = csv.size() + "";
        outCsv.add(ssL1);
        //输出文件到电脑硬盘
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
        //得到要输出文件的位置
        File csvFile = new File(outPutPath + "\\" + fileName + ".csv");
        //IO 定义输出流
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            //判断文件夹是否存在
            if (!file.exists()) {
                //不存在就创建
                if (file.mkdirs()) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            }
            //判断文件是否存在 不存在就创建
            if (!csvFile.exists()) {
                if (csvFile.createNewFile()) {
                    System.out.println("文件创建成功");
                } else {
                    System.out.println("文件创建失败");
                }
            }
            //实例化输入流 将文件输出到电脑的上的工具
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8), 1024);
            for (String[] exportDatum : exportData) {
                //一行一行进行写入操作
                writeRow(exportDatum, csvFileOutputStream);
                csvFileOutputStream.newLine();
            }
        } catch (Exception e) {
            //如果出现异常打印出来
            e.printStackTrace();
        } finally {
            try {
                if (csvFileOutputStream != null) {
                    //关闭io流
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                //如果出现异常打印出来
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
            //判断有无特殊字符 如果有就替换掉
            csvWriter.write(DelQuota(data));
            //因为csv 是用‘，’分割单元格 所以每写一个都需要 加一个 ，号
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
                //存在特殊字符就替换
                result = result.replace(s, "");
            }
        }
        return result;
    }


}



···
