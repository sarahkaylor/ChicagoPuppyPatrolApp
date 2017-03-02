package Util;

public class Lazy<T> implements ILazy<T> {
	private volatile T _value;
	private volatile boolean _set;
	private final IEvaluationStrategy<T> _evaluateFn;
	private final ISpinLock _lock;
	
	public Lazy(IEvaluationStrategy<T> evaluateFn) {
		_evaluateFn = evaluateFn;
		_lock = new SpinLock();
	}

	public T Value() throws Exception {
		if(_set) {
			return _value;
		}
		try(AutoCloseable lock = _lock.UnderLock()) {
			T value = _evaluateFn.Evaluate();
			_value = value;
			_set = true;
		}
		return _value;
	}
}
