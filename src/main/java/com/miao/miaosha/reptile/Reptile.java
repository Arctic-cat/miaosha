package com.miao.miaosha.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.miao.common.Const;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.params.HttpParams;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.http.HttpMethod;
import sun.net.www.http.HttpClient;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Reptile {
    public static List getExternalAPI(String GET_URL) {
        try {
            URL url = new URL(GET_URL);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.setRequestProperty ("providerId", "3071896");
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Const.encoding));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {// 循环读取流
                stringBuilder.append(line);
            }

            reader.close();// 关闭流
            connection.disconnect();// 断开连接

//            String xmlStr = stringBuilder.toString();
//            JSONArray jsonArray = JsonUtil.xmlStr2JsonArr(xmlStr);
//            JSONArray jsonArray = null;
//            List list = JsonUtil.getMapList(jsonArray);

            System.out.println(stringBuilder.toString());
            return null;
        } catch (Exception e) {
//            logger.error("调用外部接口出现错误", e);
            System.out.println(e);
        }
        return null;
    }

    public static List getDownLoadAPI(String GET_URL) {
        try {
            URL url = new URL(GET_URL);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Const.encoding));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {// 循环读取流
                stringBuilder.append(line);
            }

            reader.close();// 关闭流
            connection.disconnect();// 断开连接


            getMapList(stringBuilder.toString());
            System.out.println();
            return null;
        } catch (Exception e) {
//            logger.error("调用外部接口出现错误", e);
            System.out.println(e);
        }
        return null;
    }



        public static void http302(String url) {
            try {
                System.out.println("访问地址:" + url);
                URL serverUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) serverUrl
                        .openConnection();
                conn.setRequestMethod("GET");
                // 必须设置false，否则会自动redirect到Location的地址
                conn.setInstanceFollowRedirects(false);

                conn.addRequestProperty("Accept-Charset", "UTF-8;");
                conn.addRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
//                conn.addRequestProperty("Referer", "http://zuidaima.com/");
                conn.connect();
                String location = conn.getHeaderField("Location");

                serverUrl = new URL(location);
                conn = (HttpURLConnection) serverUrl.openConnection();
                conn.setRequestMethod("GET");

                conn.addRequestProperty("Accept-Charset", "UTF-8;");
                conn.addRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
//                conn.addRequestProperty("Referer", "http://zuidaima.com/");
                conn.connect();
                System.out.println("跳转地址:" + location);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public static File downloadFile(String url, String filePath,String fileName){
        filePath = "d:";
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        File file=new File(filePath);
        //判断文件夹是否存在
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

        return file;

    }


    //解析xls getWorkbook


    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

    private Workbook getWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);

        Workbook workbook = null;
        //根据后缀，得到不同的Workbook子类，即HSSFWorkbook或XSSFWorkbook
        if (filePath.endsWith(SUFFIX_2003)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(SUFFIX_2007)) {
            workbook = new XSSFWorkbook(is);
        }

        return workbook;
    }

    public void parseWorkbook(String filePath) {
        filePath = "C:/Users/mayn/Downloads/3071896_1530433384507(3).xls";
        try {
            Workbook workbook = getWorkbook(filePath);
            int numOfSheet = workbook.getNumberOfSheets();
            //依次解析每一个Sheet
            for (int i = 0; i < numOfSheet; ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                parseSheet(sheet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseSheet(Sheet sheet) {
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            String orderNumber = row.getCell(0).getStringCellValue();
            String courseName = row.getCell(3).getStringCellValue();
            String date = row.getCell(1).getStringCellValue();
            System.out.println(orderNumber);
            System.out.println(courseName);
            System.out.println(date);

        }
    }


    /**将str转化为MAP集合
     * @param str str
     * @return
     * @throws Exception
     */
    public static  List<Map<String,Object>> getMapList(String str) throws Exception    {
        Map map = new HashMap();
        List<Map<String,Object>> mapListJson = new ArrayList<>();

        Map mapType = JSON.parseObject(str,Map.class);
        System.out.println("这个是用JSON类,指定解析类型，来解析JSON字符串!!!");
        for (Object obj : mapType.keySet()){

            String strkey1 = (String) obj;
            Object strval1 = mapType.get(obj);
            map.put(strkey1, strval1);
            mapListJson.add(map);
        }



        return mapListJson;
    }

}
