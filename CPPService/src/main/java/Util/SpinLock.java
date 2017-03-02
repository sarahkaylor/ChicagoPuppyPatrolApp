package Util;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock implements ISpinLock
{
    private final AtomicReference<Thread> _lock = new AtomicReference<>(null);
    private final Lock _unlock = new Lock();
    
    public AutoCloseable UnderLock()
    {
        Thread thread = Thread.currentThread();
        while(true)
        {
            if (!_lock.compareAndSet(null,thread))
            {
                if (_lock.get()==thread)
                    throw new IllegalStateException("SpinLock is not reentrant");
                continue;
            }
            return _unlock;
        }
    }
    
    public class Lock implements AutoCloseable
    {
        @Override
        public void close()
        {
            _lock.set(null);
        }
    }
}