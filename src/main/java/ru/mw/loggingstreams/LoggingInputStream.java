package ru.mw.loggingstreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nixan on 11.12.13.
 */
public class LoggingInputStream extends InputStream {

    private final InputStream mInputStream;

    private final OutputStream mLoggerStream;

    /**
     * Class that provides on-fly logging for the data that goes through the InputStream
     *
     * @param inputStream  wrapped stream
     * @param loggerStream stream for logging
     */
    public LoggingInputStream(InputStream inputStream, OutputStream loggerStream) {
        mInputStream = inputStream;
        mLoggerStream = loggerStream;
    }

    @Override
    public int read() throws IOException {
        int result = mInputStream.read();
        if (result > 0) {
            mLoggerStream.write(result);
        }
        return result;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        int result = mInputStream.read(bytes);
        if (result > 0) {
            mLoggerStream.write(bytes, 0, result);
        }
        return result;
    }

    @Override
    public int read(byte[] bytes, int i, int i2) throws IOException {
        int result = mInputStream.read(bytes, i, i2);
        if (result > 0) {
            mLoggerStream.write(bytes, i, result);
        }
        return result;
    }

    @Override
    public long skip(long l) throws IOException {
        if (l > 0) {
            throw new UnsupportedOperationException();
        }
        return mInputStream.skip(l);
    }

    @Override
    public void close() throws IOException {
        mInputStream.close();
        mLoggerStream.write("\n".getBytes());
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void mark(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int available() throws IOException {
        return mInputStream.available();
    }
}
