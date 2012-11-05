package com.thoughtworks.scmviz.sparkline;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.List;

public class Sparkline {

    private static final int BAR_WIDTH = 4;

    public final int width;
    public final int height;

    private final Graphics2D graphics;
    private BufferedImage image;

    public Sparkline(int months, int cap) {
        width = months * (BAR_WIDTH + 1);
        height = cap;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    public RenderedImage render(List<DataPoint> points) {
        graphics.setColor(new Color(240, 240, 240));
        graphics.fillRect(0, 0, width, height);
        int x = 0;
        for (DataPoint point : points) {
            drawBar(x++, point);
        }
        return image;
    }

    private void drawBar(int x, DataPoint point) {
        Color color;
        if (point.isTick()) {
            color = Color.black;
        } else if (point.getValue() == null) {
            color = new Color(200, 200, 200);
        } else {
            color = new Color(160, 160, 160);
        }
        graphics.setColor(color);

        int barHeight = point.getValue() != null ? point.getValue() : 1;
        graphics.fillRect(x * (BAR_WIDTH + 1), height - barHeight, BAR_WIDTH, barHeight);
    }

}

