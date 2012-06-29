package lawscraper.server.scrapers;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Helper to read laws and caselaws from ZIP.
 */
public class ZipDataUtil {

    private static ZipFile zipFile;

    private static ZipFile getLawZipFile() throws IOException {
        if (zipFile == null) {
            File zipFile = getFile("/lawscraper/all_laws.zip");
            ZipDataUtil.zipFile = new ZipFile(zipFile);
        }
        return ZipDataUtil.zipFile;
    }

    private static ZipFile getCaseLawZipFile() throws IOException {
        if (zipFile == null) {
            File zipFile = getFile("/lawscraper/all_caselaws.zip");
            ZipDataUtil.zipFile = new ZipFile(zipFile);
        }
        return ZipDataUtil.zipFile;
    }

    public static File getFile(String classPath) throws IOException {
        File tempFile = File.createTempFile("test", ".dat");
        tempFile.deleteOnExit();
        InputStream inputStream = ZipDataUtil.class.getResourceAsStream(classPath);
        IOUtils.copyLarge(inputStream, new FileOutputStream(tempFile));
        return tempFile;
    }

    public static Iterable<LawEntry> getAllLaws() throws IOException {
        final Enumeration<? extends ZipEntry> enumeration = getLawZipFile().entries();
        return new Iterable<LawEntry>() {
            @Override
            public Iterator<LawEntry> iterator() {
                return new LawIterator(enumeration);
            }
        };
    }

    public static Iterable<CaseLawEntry> getAllCaseLaws() throws IOException {
        final Enumeration<? extends ZipEntry> enumeration = getCaseLawZipFile().entries();
        enumeration.nextElement();
        return new Iterable<CaseLawEntry>() {
            @Override
            public Iterator<CaseLawEntry> iterator() {
                return new CaseLawIterator(enumeration);
            }
        };
    }

    public static InputStream getLaw(String lawName) throws IOException {
        ZipEntry entry = getLawZipFile().getEntry(lawName + ".xht2");
        return getLawZipFile().getInputStream(entry);
    }

    public static InputStream getCaseLaw(String lawName) throws IOException {
        ZipEntry entry = getLawZipFile().getEntry(lawName + ".xht2");
        return getLawZipFile().getInputStream(entry);
    }


    private static class LawIterator implements Iterator<LawEntry> {
        private final Enumeration<? extends ZipEntry> entries;

        public LawIterator(Enumeration<? extends ZipEntry> enumeration) {
            entries = enumeration;
        }

        @Override
        public boolean hasNext() {
            return entries.hasMoreElements();
        }

        @Override
        public LawEntry next() {
            ZipEntry zipEntry = entries.nextElement();
            return new LawEntry(zipEntry);
        }

        @Override
        public void remove() {
        }
    }

    private static class CaseLawIterator implements Iterator<CaseLawEntry> {
        private final Enumeration<? extends ZipEntry> entries;

        public CaseLawIterator(Enumeration<? extends ZipEntry> enumeration) {
            entries = enumeration;
        }

        @Override
        public boolean hasNext() {
            return entries.hasMoreElements();
        }

        @Override
        public CaseLawEntry next() {
            ZipEntry zipEntry = entries.nextElement();
            return new CaseLawEntry(zipEntry);
        }

        @Override
        public void remove() {
        }
    }


    public static class LawEntry {
        private final ZipEntry zipEntry;

        public LawEntry(ZipEntry zipEntry) {
            this.zipEntry = zipEntry;
        }

        public InputStream getInputStream() throws IOException {
            return getLawZipFile().getInputStream(zipEntry);
        }

        public String getName() {
            return zipEntry.getName();
        }
    }

    public static class CaseLawEntry {
        private final ZipEntry zipEntry;

        public CaseLawEntry(ZipEntry zipEntry) {
            this.zipEntry = zipEntry;
        }

        public InputStream getInputStream() throws IOException {
            return getCaseLawZipFile().getInputStream(zipEntry);
        }

        public String getName() {
            return zipEntry.getName();
        }
    }
}
