import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ObserverPattern {
	public static void main(String[] args) {
		Publisher p = new Publisher();
		DateListener s = new DateListener();
		
		p.addSubscriber(EventType.date, new DateListener());
		p.addSubscriber(EventType.string, new StringListener());

		// necesitamos solo pasar
		// p.setState(new DateContext(LocalDateTime.now()), EventType.date);
		p.setState(new StringContext("asd"), EventType.string);

	}
}

class Context {
	LocalDateTime date = LocalDateTime.now();
	String aStringToBeListened = "Pene";

	public Context(LocalDateTime date) {
		this.date = date;
	}

	public Context(String s) {
		this.aStringToBeListened = s;
	}

	public Context(LocalDateTime date, String s) {
		this.date = date;
		this.aStringToBeListened = s;
	}

}

class DateContext extends Context {
	public DateContext(LocalDateTime date) {
		super(date);
	}
}
class StringContext extends Context {
	public StringContext(String s) {
		super(s);
	}
}

interface Subscriber<T extends Context> {
	void update(T context);
}

// tipos de eventos (importante)
enum EventType {
	date,
	string
}

class Publisher {
	// cada event type va a tener una lista de suscriptores, algo asi:
	// 
	// "date": ArrayList<Subscriber> // lista de suscriptores del evento "date"
	HashMap<EventType, ArrayList<Subscriber<? extends Context>>> subscribers = new HashMap<>();

	private Context state = new Context(LocalDateTime.now(), "aaa");

	// al anadir un suscriptor, acepta una subclase de Context o context itself
	<T extends Context> void addSubscriber(EventType et, Subscriber<T> s) {
		// si el et no esta en el hashmap, creamos un arraylist y anadimos el suscriptor
		subscribers.computeIfAbsent(et, k -> new ArrayList<>()).add(s);
	}

	
	// igual aca
	<T extends Context> void removeSubscriber(EventType et, Subscriber<T> s) {
		subscribers.computeIfAbsent(et, k -> new ArrayList<>()).remove(s);
	}

	// toma una subclase de Context (ver setState())
	<T extends Context> void notifySubscribers(T context, EventType et) {
		// obtener la lista que tiene que ver con ese evento
		
		// como subscribers.get() devuelve un ArrayList<Subscriber<? extends Context>>, debemos declarar listRelated asi porque
		// no se sabe el tipo del contexto, y no podemos usar T por el retorno de subscribers.get(et)
		ArrayList<Subscriber<? extends Context>> listRelated = subscribers.get(et);
		if (listRelated == null) return;

		
		for (Subscriber<? extends Context> s : listRelated) {
			// updatear a cada subscriber
			@SuppressWarnings("unchecked")
			// casteamos subscriber<?> a subscriber<T extends Context>
			Subscriber<T> subscriber = (Subscriber<T>) s;
			// ya estamos seguro del contexto que se esta usando
			subscriber.update(context);
			
		}
	}

	// acepta un contexto que sea una subclase de context o context asimismo
	<T extends Context> void setState(T newContext, EventType eventType) {
		this.state = newContext;
		notifySubscribers(newContext, eventType);
	}
}

class DateListener implements Subscriber<DateContext> {

    @Override
    public void update(DateContext context) {
        System.out.println("DateListener notified! ");
		System.out.println(context.date);
		System.out.println(context.aStringToBeListened);

    }
	
}

class StringListener implements Subscriber<StringContext> {

    @Override
    public void update(StringContext context) {
        System.out.println("StringListener notified!");
		System.out.println(context.aStringToBeListened);
    }
	
}



