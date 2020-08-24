package net.zoizoi.java.mapeditorsvg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import org.w3c.dom.DOMImplementation;

// SVG
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;

public class Main {
  public static void main(String[] args) throws Exception {
    createPNG();
    createSVG();
  }

  /**
   * 描画します。
   *
   * @param g Graphics2D オブジェクト
   * @param w 描画範囲の横幅
   * @param h 描画範囲の縦幅
   */
  private static void draw(Graphics2D g, double w, double h) {
    // 線の太さ
    g.setStroke(new BasicStroke((float) (w + h) / 20.0f));
    // 背景
    g.setPaint(Color.LIGHT_GRAY);
    g.fill(new Rectangle2D.Double(0, 0, w, h));
    // 枠線
    g.setPaint(Color.BLUE);
    g.draw(new Rectangle2D.Double(0, 0, w, h));
    // 円
    g.setPaint(Color.RED);
    g.fill(new Ellipse2D.Double(w / 4, h / 4, w / 2, h / 2));
    // 線
    g.setPaint(new Color(255, 255, 0, 100));
    Line2D line = new Line2D.Double(w / 8, h / 8, w / 2, h / 2);
    g.draw(line);
  }

  /**
   * PNG (Portable Network Graphics) ファイルを出力します。
   */
  private static void createPNG() throws IOException {
    int w = 297 * 2;
    int h = 210 * 2;
    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    draw(g, w, h);
    ImageIO.write(image, "png", new File("png.png"));
  }

  /**
   * SVG (Scalable Vector Graphics) ファイルを出力します。
   */
  private static void createSVG() throws IOException {
    int w = 297 * 2;
    int h = 210 * 2;
    DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    org.w3c.dom.Document doc = domImpl.createDocument(svgNS, "svg", null);
    SVGGraphics2D g = new SVGGraphics2D(doc);
    g.setSVGCanvasSize(new Dimension(w, h));
    draw(g, w, h);
    g.stream("svg.svg");
  }
}
