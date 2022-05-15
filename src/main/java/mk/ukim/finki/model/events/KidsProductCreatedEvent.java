package mk.ukim.finki.model.events;

import lombok.Getter;
import mk.ukim.finki.model.KidsProduct;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class KidsProductCreatedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public KidsProductCreatedEvent(KidsProduct source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public KidsProductCreatedEvent(KidsProduct source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
