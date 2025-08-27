package cc.sika.file.service.impl.file;

import cc.sika.file.exception.BaseRuntimeException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 阿里OSS文件上传进度追踪器
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Slf4j
public class ProgressTracker implements ProgressListener {

    @Getter
    private final FluxSink<Integer> sink;
    /**
     * 已上传字节数
     */
    private final AtomicLong updatedBytes = new AtomicLong(0);
    /**
     * 需要上传的总文件字节数
     */
    private final AtomicLong totalBytes;

    public ProgressTracker(FluxSink<Integer> sink, long fileSize) {
        this.sink = sink;
        totalBytes = new AtomicLong(fileSize);
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                log.debug("Transfer started");
                sink.next(0);
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                totalBytes.set(progressEvent.getBytes());
                log.info("{} bytes in total will be uploaded to OSS", this.totalBytes);
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                long thisTimeWritten = progressEvent.getBytes();
                long written = this.updatedBytes.addAndGet(thisTimeWritten);
                if (this.totalBytes.get() != -1) {
                    int percent = (int)(written * 100.0 / this.totalBytes.get());
                    sink.next(percent);
                    log.debug("{} bytes have been written at this time, upload progress: {}%({}/{})",
                            thisTimeWritten,
                            percent,
                            this.updatedBytes.get(),
                            this.totalBytes.get());
                }
                else {
                    log.warn("{}  bytes have been written at this time, upload ratio: unknown({}/...)",
                            thisTimeWritten,
                            this.updatedBytes.get());
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                sink.next(100);
                sink.complete();
                log.debug("Succeed to upload, {} bytes have been transferred in total", this.updatedBytes.get());
                break;
            case TRANSFER_FAILED_EVENT:
                sink.error(new BaseRuntimeException("Upload failed"));
                log.error("Failed to upload, {} bytes have been transferred", this.updatedBytes.get());
                break;
            default:
                break;
        }
    }
}
