import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

public class Filters {

    private static void applyMask(BufferedImage image, BufferedImage valueImage, int maskSize, Function<Double[][], Integer> operation) {
        Double[][] red = new Double[maskSize][maskSize];
        Double[][] green = new Double[maskSize][maskSize];
        Double[][] blue = new Double[maskSize][maskSize];
        for (int y = 0; y < maskSize; y++) {
            for (int x = 0; x < maskSize; x++) {
                red[x][y] = 0D;
                green[x][y] = 0D;
                blue[x][y] = 0D;
            }
        }
        int x0, y0, argb;
        for (int y = 0; y < valueImage.getHeight(); y++) {
            for (int x = 0; x < valueImage.getWidth(); x++) {
                for (int my = 0; my < maskSize; my++) {
                    for (int mx = 0; mx < maskSize; mx++) {
                        x0 = Math.abs(x + mx - maskSize / 2);
                        y0 = Math.abs(y + my - maskSize / 2);
                        if (x0 >= valueImage.getWidth()) {
                            x0 -= 2 * mx;
                        }
                        if (y0 >= valueImage.getHeight()) {
                            y0 -= 2 * my;
                        }
                        argb = valueImage.getRGB(x0, y0);
                        red[mx][my] = (double) ((argb >> 16) & 0xFF);
                        green[mx][my] = (double) ((argb >> 8) & 0xFF);
                        blue[mx][my] = (double) (argb & 0xFF);
                    }
                }
                image.setRGB(x, y,
                        0xFF000000 | operation.apply(red) << 16 | operation.apply(green) << 8 | operation.apply(blue));
            }
        }
    }

    public static void convolution(BufferedImage image, BufferedImage valueImage, Double[][] mask) {
        double maskSum = 0;
        for (int y = 0; y < mask.length; y++) {
            for (int x = 0; x < mask.length; x++) {
                maskSum += mask[x][y];
            }
        }
        if (maskSum != 0) {
            for (int y = 0; y < mask.length; y++) {
                for (int x = 0; x < mask.length; x++) {
                    mask[x][y] /= maskSum;
                }
            }
        }
        applyMask(image, valueImage, mask.length, pixels -> {
            double val = 0;
            for (int y = 0; y < mask.length; y++) {
                for (int x = 0; x < mask.length; x++) {
                    val += pixels[x][y] * mask[x][y];
                }
            }
            return Math.min(255, Math.max(0, (int) val));
        });
    }

    public static void medianFilter(BufferedImage image, BufferedImage valueImage, int maskSize) {
        applyMask(image, valueImage, maskSize, pixels -> {
            ArrayList<Double> list = new ArrayList<>();
            for (int y = 0; y < maskSize; y++) {
                for (int x = 0; x < maskSize; x++) {
                    list.add(pixels[x][y]);
                }
            }
            list.sort(new Comparator<Double>() {

                @Override
                public int compare(Double arg0, Double arg1) {
                    return (int) (arg0 - arg1);
                }
            });
            return list.get(list.size() / 2).intValue();
        });

    }

}
