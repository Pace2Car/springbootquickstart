package com.pace2car.springbootdemo.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 带背景二维码生成工具
 *
 * @author chenjiahao
 * @date 2019/7/25 14:27
 */
public class QrCodeUtil {

    /**
     * 二维码颜色:黑色
     */
    private static final int QR_COLOR = 0x201f1f;
    /**
     * 二维码背景颜色:白色
     */
    private static final int BG_COLOR = 0xFFFFFF;

    /**
     * 初始化设置QR二维码参数信息
     */
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;

        {
            // 设置QR二维码的纠错级别(H为最高级别)
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置编码方式
            put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 白边
            put(EncodeHintType.MARGIN, 0);
        }
    };

    /**
     * 生成二维码图片+背景+文字描述
     *
     * @param qrCodeImg 生成图地址
     * @param bgImg     背景图地址
     * @param qrWid   二维码宽度
     * @param qrHeight  二维码高度
     * @param qrUrl     二维码识别地址
     * @param note1     文字描述1
     * @param note2     文字描述2
     * @param size      文字大小
     * @param qrStartX  二维码x轴方向
     * @param qrStartY  二维码y轴方向
     * @param text1X    文字描述1x轴方向
     * @param text1Y    文字描述1y轴方向
     * @param text2X    文字描述2x轴方向
     * @param text2Y    文字描述2y轴方向
     */
    public static void CreatQRCode(File qrCodeImg, File bgImg, Integer qrWid, Integer qrHeight, String qrUrl,
                                   String note1, String note2, Integer size, Integer qrStartX, Integer qrStartY,
                                   Integer text1Y, Integer text2Y) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为: 编码内容,编码类型,生成图片宽度,生成图片高度,设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, qrWid, qrHeight, hints);
            BufferedImage image = new BufferedImage(qrWid, qrHeight, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑(0xFFFFFFFF) 白(0xFF000000)两色
            for (int x = 0; x < qrWid; x++) {
                for (int y = 0; y < qrHeight; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QR_COLOR : BG_COLOR);
                }
            }

            // 添加背景图片
            BufferedImage backgroundImage = ImageIO.read(bgImg);
            int bgWidth = backgroundImage.getWidth();
            int qrWidth = image.getWidth();
            // 距离背景图片x边的距离，居中显示
            int disx = (bgWidth - qrWidth) - qrStartX;
            // 距离y边距离 * * * *
            int disy = qrStartY;
            Graphics2D rng = backgroundImage.createGraphics();
            rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            rng.drawImage(image, disx, disy, qrWidth, qrHeight, null);

            // 文字描述参数设置
            Color textColor = Color.black;
            rng.setColor(textColor);
            rng.drawImage(backgroundImage, 0, 0, null);
            // 设置字体类型和大小(BOLD加粗/ PLAIN平常)
            rng.setFont(new Font("微软雅黑,Arial", Font.BOLD, size));
            // 设置字体颜色
            rng.setColor(Color.black);
            int strWidth1 = rng.getFontMetrics().stringWidth(note1);
            int strWidth2 = rng.getFontMetrics().stringWidth(note2);

            // 文字1显示位置
            // 左右居中
            int text1X = (bgWidth - strWidth1) / 2;
            // 上下
            rng.drawString(note1, text1X, text1Y);

            // 设置字体颜色
            rng.setColor(Color.green);
            // 文字2显示位置
            // 左右居中
            int text2X = (bgWidth - strWidth2) / 2;
            // 上下
            rng.drawString(note2, text2X, text2Y);

            rng.dispose();
            image = backgroundImage;
            image.flush();
            ImageIO.write(image, "png", qrCodeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 背景图片
        File bgImgFile = new File("./qrcode/bg.png");
        // 生成图片位置
        File qrCodeFile = new File("./qrcode/qrcode.png");
        // 二维码链接
        String url = "https://pace2car.com";
        // 文字描述1
        String note1 = "pace2car.com";
        // 文字描述2
        String note2 = "欢迎光临";

        // 宣传二维码生成
        // 生成图地址,背景图地址,二维码宽度,二维码高度,二维码识别地址,文字描述1,文字描述2,文字大小,图片x轴方向,图片y轴方向,文字1||2xy轴方向
        CreatQRCode(qrCodeFile, bgImgFile,
                149,
                149,
                url,
                note1, note2,
                22,
                67,
                142,
                110,
                320);
    }
}
