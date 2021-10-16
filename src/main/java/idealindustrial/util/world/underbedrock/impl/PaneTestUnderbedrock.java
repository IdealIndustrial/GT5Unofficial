package idealindustrial.util.world.underbedrock.impl;


import idealindustrial.util.misc.RandomCollection;
import idealindustrial.util.world.underbedrock.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PaneTestUnderbedrock {

    private static final int cellSize = 10;
    private static int xOff = 0, yOff = 0;
    private final static boolean regenerate = true;
    private static final int gridSize = 10, clusterSize = 15;

    private static UnderbedrockLayer<ColoredCell> provideLayer() {
        RandomCollection<VeinProvider<ColoredCell>> random = new RandomCollection<>(0);

        random.add(3, new SquareVeinProvider<>(i -> new ColoredCell(Color.RED), 0, 100).setOffsets(0, 4).setSizes(1, 3));
        GridGenerationRules<ColoredCell> rules = new GridGenerationRules<ColoredCell>() {
            @Override
            public int getPassCount() {
                return 1;
            }

            @Override
            public WeightedRandom<VeinProvider<ColoredCell>> getProviderForPass(int pass) {
                return random.copy();
            }

            @Override
            public OrderGenerator getOrder(int size, Random random) {
                return new RandomOrder(size, random);
            }

            @Override
            public int getGridSize() {
                return gridSize;
            }

            @Override
            public int getClusterSize() {
                return clusterSize;
            }
        };
        return new BasicUnderbedrockLayer<>(rules);
    }

    private static class ColoredCell {
        Color color;


        public ColoredCell(Color color) {
            this.color = color;
        }

        public void render(Graphics2D graphics2D, int x, int y) {
            graphics2D.setColor(color);
            graphics2D.fillRect(x + 1, y + 1, cellSize - 1, cellSize - 1);
        }
    }

    private static class Renderer {
        private UnderbedrockLayer<ColoredCell> world;
        private final int xSize;
        private final int ySize;

        public Renderer(UnderbedrockLayer<ColoredCell> world, int xSize, int ySize) {
            this.world = world;
            this.xSize = xSize;
            this.ySize = ySize;
        }

        public void render(Graphics2D graphics2D) {
            if (regenerate) {
                world = provideLayer();
            }
            int xPixelSize = xSize * cellSize, yPixelSize = ySize * cellSize;
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, xPixelSize, yPixelSize);

            graphics2D.setColor(Color.GRAY);
            for (int x = 0; x < xSize; x++) {
                graphics2D.drawLine(x * cellSize, 0, x * cellSize, yPixelSize);
            }
            for (int y = 0; y < ySize; y++) {
                graphics2D.drawLine(0, y * cellSize, xPixelSize, y * cellSize);
            }
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    ColoredCell cell = world.get(xOff + x, yOff + y);
                    if (cell == null) {
                        if ((x + xOff) % (clusterSize * gridSize) == 0 || (y + yOff) % (clusterSize * gridSize) == 0) {
                            graphics2D.setColor(Color.DARK_GRAY);
                            graphics2D.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 1, cellSize - 1);
                            continue;
                        }
                        if ((x + xOff) % gridSize == 0 || (y + yOff) % gridSize == 0) {
                            graphics2D.setColor(Color.GRAY);
                            graphics2D.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 1, cellSize - 1);
                            continue;
                        }
                        continue;
                    }
                    cell.render(graphics2D, x * cellSize, y * cellSize);
                }
            }

        }
    }

    private static class TestPane extends JPanel {

        int x, y;

        Renderer reference;

        public TestPane(int x, int y) {
            Timer timer = new Timer(100, e -> repaint());
            timer.start();
            this.x = cellSize * x;
            this.y = cellSize * y;
            UnderbedrockLayer<ColoredCell> layer = provideLayer();
            reference = new Renderer(layer, x, y);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(x, y);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage image = new BufferedImage(x, y, 3);
            if (reference == null)
                return;
            reference.render(image.createGraphics());
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            g.drawImage(image, 0, 0, null);


        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            JFrame frame = new JFrame("Testing");
            frame.setLayout(new GridBagLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            TestPane pane = new TestPane(150, 100);
            frame.add(pane, new GridBagConstraints());
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    switch (e.getKeyChar()) {
                        case 'w':
                            yOff--;
                            break;
                        case 's':
                            yOff++;
                            break;
                        case 'd':
                            xOff++;
                            break;
                        case 'a':
                            xOff--;
                            break;
                    }

                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
