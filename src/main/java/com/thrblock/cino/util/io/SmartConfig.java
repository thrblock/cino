package com.thrblock.cino.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SmartConfig用于在异构平台上发布配置文件<br />
 * 读取拓展名为smt的配置脚本，并根据操作系统创建对应外部路径，并将配置文件复制到匹配的位置<br />
 * 对要包装配置文件格式无要求，只要是文本构成的，最终可以获得实际位置的流对象<br />
 * SmartConfig会以外部文件内容为最优先
 * @author lizepu
 */
public class SmartConfig {
    private static final String ENCODING = "UTF-8";
    private static final Logger LOG = LoggerFactory
            .getLogger(SmartConfig.class);
    private String source;
    private String os = System.getProperty("os.name").toUpperCase();
    private String location;
    
    /**
     * 构造一个SmartConfig实例
     * @param source 基于class的资源相对位置叙述
     */
    public SmartConfig(String source) {
        this.source = source;
        LOG.info("source:" + source);
    }

    public Reader getConfigAsReader() throws IOException {
        return new InputStreamReader(getConfigAsInputStream(),ENCODING);
    }
    public InputStream getConfigAsInputStream() throws IOException {
        if(checkMstStructure()) {
            if(location != null) {
                return seekLocation();
            } else {
                return seekDefault();
            }
        } else {
            return getClass().getClassLoader().getResourceAsStream(source);
        }
    }
    
    private InputStream seekDefault() throws IOException {
        ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        BufferedWriter bytewriter = new BufferedWriter(new OutputStreamWriter(byteos,ENCODING));
        InputStream smtStream = getClass().getClassLoader().getResourceAsStream(source);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                smtStream, ENCODING))) {
            String line = reader.readLine();
            while(!"#SMT END".equalsIgnoreCase(line)) {
                LOG.debug("skip smt expression:" + line);
                line = reader.readLine();
            }
            
            line = reader.readLine();
            while(line != null) {
                bytewriter.write(line);
                bytewriter.newLine();
                line = reader.readLine();
            }
            bytewriter.flush();
        }
        return new ByteArrayInputStream(byteos.toByteArray());
    }

    private InputStream seekLocation() throws IOException {
        File dstFile = new File(location);
        if(dstFile.exists()) {
            return new FileInputStream(dstFile);
        } else {
            InputStream stream = seekDefault();
            File folder = dstFile.getParentFile();
            if(!folder.exists() && !folder.mkdirs()) {
                LOG.warn("fail to create folder:" + folder);
                return stream;
            }
            if(!dstFile.createNewFile()) {
                LOG.warn("fail to create file:" + dstFile);
                return stream;
            }
            LOG.info("export config file:" + dstFile);
            byte[] cache = new byte[1024];
            try(OutputStream fos = new FileOutputStream(dstFile)) {
                for(int realRead = 0;realRead > -1;realRead = stream.read(cache)) {
                    fos.write(cache, 0, realRead);
                }
                fos.flush();
            }
            return new FileInputStream(dstFile);
        }
    }

    /**
     * check mst file structure is illegal
     * @return booelan
     * @throws IOException 
     */
    private boolean checkMstStructure() throws IOException {
        InputStream smtStream = getClass().getClassLoader().getResourceAsStream(source);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                smtStream, ENCODING))) {
            String begin = reader.readLine();
            if (begin == null || !"#SMT BEGIN".equalsIgnoreCase(begin)) {
                LOG.warn("error smt title:" + begin);
                return false;
            }
            String osma = reader.readLine();
            while(!"#SMT END".equalsIgnoreCase(osma)){
                if(!processOSMapping(osma)) {
                    LOG.warn("error mst structure!");
                    return false;
                }
                osma = reader.readLine();
            }
            return true;
        }
    }

    private boolean processOSMapping(String line) {
        if(line == null) {
            return false;
        }
        String[] param = line.split(" ");
        if (param.length == 3 && "#OSMAPPING".equalsIgnoreCase(param[0])) {
            if(os.indexOf(param[1].toUpperCase()) != -1) {
                LOG.info("OS Mapped:" + os + ",Location:" + param[2]);
                this.location = param[2];
            }
            return true;
        } else {
            LOG.warn("illegal smt expression:" + line);
            return false;
        }
    }
}
