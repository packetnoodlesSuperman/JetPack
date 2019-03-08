package com.jetpack.xhb.okhttp.internal;

public abstract class NamedRunnable implements Runnable {

    protected final String name;

    public NamedRunnable(String format, Object... args) {
        this.name = Util.format(format, args);
    }

    @Override
    public void run() {
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(name);

        try {
            execute();
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }

    protected abstract void execute();
}
