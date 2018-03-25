package io.huta.application.infra;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleThreadFactoryBuilder {
    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    public SimpleThreadFactoryBuilder() {
    }

    public SimpleThreadFactoryBuilder nameFormat(String nameFormat) {
        String zero = format(nameFormat, 0);
        this.nameFormat = nameFormat;
        return this;
    }

    public SimpleThreadFactoryBuilder deamon(Boolean deamon) {
        this.daemon = deamon;
        return this;
    }

    public SimpleThreadFactoryBuilder uncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        Objects.requireNonNull(uncaughtExceptionHandler, "UncaughtExceptionHandler cannot be null");
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public SimpleThreadFactoryBuilder threadFactory(ThreadFactory backingThreadFactory) {
        Objects.requireNonNull(backingThreadFactory, "ThreadFactory cannot be null");
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    public SimpleThreadFactoryBuilder priority(int priority) {
        if (priority < Thread.MIN_PRIORITY && priority > Thread.MAX_PRIORITY) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Priority %d have to be between %d and %d "
                            , priority
                            , Thread.MIN_PRIORITY
                            , Thread.MAX_PRIORITY
                    )
            );
        }
        this.priority = priority;
        return this;
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }

    public ThreadFactory build() {
        return doBuild(this);
    }

    private static ThreadFactory doBuild(SimpleThreadFactoryBuilder builder) {
        final String nameFormat = builder.nameFormat;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory =
                (builder.backingThreadFactory != null)
                        ? builder.backingThreadFactory
                        : Executors.defaultThreadFactory();
        final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory.newThread(runnable);
                if (nameFormat != null) {
                    thread.setName(format(nameFormat, count.getAndIncrement()));
                }
                if (daemon != null) {
                    thread.setDaemon(daemon);
                }
                if (priority != null) {
                    thread.setPriority(priority);
                }
                if (uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }
                return thread;
            }
        };
    }
}
