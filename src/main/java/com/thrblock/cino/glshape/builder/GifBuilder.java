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

public class GifBuilder {
    private GifBuilder(){
    }
    public static class GifData {
        private BufferedImage[] images;
        private int number;
        private int rate;
        public BufferedImage[] getImages() {
            return images;
        }
        public void setImages(BufferedImage[] images) {
            this.images = images;
        }
        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
        public int getRate() {
            return rate;
        }
        public void setRate(int rate) {
            this.rate = rate;
        }
    }
    
    public static GifData buildGifData(File file) throws IOException {
        return buildGifData(new FileInputStream(file));
    }
    
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
