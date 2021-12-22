package io.renren.common.jaxb;

import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @description: FileUtil
 * @author: chenning
 * @create: 2019-12-24 18:04
 **/
public class FileUtil {

    /**
     * @description 创建xml文件
     * @author chenning
     * @date 2020/2/27 13:16
     */
    public static String createFile(String fileName, String xml) {
        File path;
        String fileUrl = null;
        OutputStreamWriter osw = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            fileUrl = path + "/xml/" + fileName;
            File file = new File(fileUrl);
            FileOutputStream fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(xml);
            osw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    /**
     * 下载项目根目录下xml下的文件
     *
     * @param response response
     * @param fileName 文件名
     * @return 返回结果 成功或者文件不存在
     */
    public static String downloadFile(HttpServletResponse response, String fileName) {
        File path;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(path + "/xml/" + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            return "系统找不到指定的文件";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
