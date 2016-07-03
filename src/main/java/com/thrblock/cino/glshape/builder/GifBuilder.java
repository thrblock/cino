package com.thrblock.cino.glshape.builder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

/**
 * 一个读取GIF格式的工具类
 * @author lizepu
 */
public class GifBuilder {
    private GifBuilder(){
    }
    /**
     * 格式化后的Gif数据对象
     * @author lizepu
     */
    public static class GifData {
        private BufferedImage[] images;
        private int number;
        private int rate;
        /**
         * 获得Gif图像数组，该数组包括了全部Gif帧，对于某些压缩处理过的Gif文件而言，帧可能不会对应完整的一幅图像
         * @return BufferedImage[] Gif帧
         */
        public BufferedImage[] getImages() {
            return images;
        }
        /**
         * 设置Gif图像帧
         * @param images Gif图像帧
         */
        public void setImages(BufferedImage[] images) {
            this.images = images;
        }
        /**
         * 获得Gif帧数量
         * @return
         */
        public int getNumber() {
            return number;
        }
        /**
         * 设置Gif帧数量
         * @param number Gif帧数量
         */
        public void setNumber(int number) {
            this.number = number;
        }
        /**
         * 获得 播放速率，定义为 刷新频率为1秒60次时，应间隔多少次刷新次数后切换帧
         * @return 播放速率
         */
        public int getRate() {
            return rate;
        }
        /**
         * 设置 播放频率
         * @param rate 播放频率
         */
        public void setRate(int rate) {
            this.rate = rate;
        }
    }
    
    /**
     * 由文件构造一个格式化Gif数据
     * @param file Gif文件
     * @return 格式化Gif数据
     * @throws IOException 当IO异常时抛出
     */
    public static GifData buildGifData(File file) throws IOException {
        return buildGifData(new FileInputStream(file));
    }
    
    /**
     * 由Gif格式流构造一个格式化Gif数据
     * @param gifSrc Gif格式流
     * @return 格式化Gif数据
     * @throws IOException 当IO异常时抛出
     */
    public static GifData buildGifData(InputStream gifSrc) throws IOException {
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream ciis = ImageIO.createImageInputStream(gifSrc);
        reader.setInput(ciis);

        IIOMetadata imageMetaData = reader.getImageMetadata(0);
        String metaFormatName = imageMetaData.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        int delayTime = Integer.parseInt(graphicsControlExtensionNode.getAttribute("delayTime"));

        int noi = reader.getNumImages(true);
        BufferedImage[] images = new BufferedImage[noi];
        for (int i = 0; i < noi; i++) {
            images[i] = reader.read(i);
        }
        GifData data = new GifData();
        data.setImages(images);
        data.setNumber(noi);
        data.setRate(delayTime);
        
        gifSrc.close();
        return data;
    }
    
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return (IIOMetadataNode) rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return node;
    }
}
