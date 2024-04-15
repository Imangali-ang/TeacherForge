package kz.teacher.forge.teacherforge.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }

        try {
            outputStream.close();
        } catch (Exception ignored) {
        }

        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int size = inflater.inflate(tmp);
                outputStream.write(tmp, 0, size);
            }
        } catch (Exception e) {
            // Обработка исключения, например, если данные не могут быть декомпрессированы
            // Это может быть IOException или DataFormatException
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {
            }
        }

        return outputStream.toByteArray();
    }
}
