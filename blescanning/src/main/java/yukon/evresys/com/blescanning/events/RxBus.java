package yukon.evresys.com.blescanning.events;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Paul on 06.04.17. for project Evresys
 */

public class RxBus {

    public RxBus() {
    }

    private PublishSubject<Object> _bus = PublishSubject.create();

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }

    public void sendError(Throwable e) {
        _bus.onError(e);
    }


}
