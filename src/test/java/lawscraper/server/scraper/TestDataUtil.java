package lawscraper.server.scraper;

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
 * Helper to read test laws from ZIP.
 */
public class TestDataUtil {

    private static ZipFile zipFile;

    private static ZipFile getZipFile() throws IOException {
        if (zipFile == null) {
            File zipFile = File.createTempFile("test_laws", ".zip");
            zipFile.deleteOnExit();
            InputStream zipResource = TestDataUtil.class.getResourceAsStream("/lawscraper/all_laws.zip");
            IOUtils.copyLarge(zipResource, new FileOutputStream(zipFile));
            TestDataUtil.zipFile = new ZipFile(zipFile);
        }
        return TestDataUtil.zipFile;
    }

    public static Iterable<Law> getAllLaws() throws IOException {
        final Enumeration<? extends ZipEntry> enumeration = getZipFile().entries();
        return new Iterable<Law>() {
            @Override
            public Iterator<Law> iterator() {
                return new LawIterator(enumeration);
            }
        };
    }

    public static InputStream getLaw(String lawName) throws IOException {
        ZipEntry entry = getZipFile().getEntry(lawName + ".xht2");
        return getZipFile().getInputStream(entry);
    }


    private static class LawIterator implements Iterator<Law> {
        private final Enumeration<? extends ZipEntry> entries;

        public LawIterator(Enumeration<? extends ZipEntry> enumeration) {
            entries = enumeration;
        }

        @Override
        public boolean hasNext() {
            return entries.hasMoreElements();
        }

        @Override
        public Law next() {
            ZipEntry zipEntry = entries.nextElement();
            return new Law(zipEntry);
        }

        @Override
        public void remove() {
        }
    }


    public static class Law {
        private final ZipEntry zipEntry;

        public Law(ZipEntry zipEntry) {
            this.zipEntry = zipEntry;
        }

        public InputStream getInputStream() throws IOException {
            return getZipFile().getInputStream(zipEntry);
        }

        public String getName() {
            return zipEntry.getName();
        }
    }
}
