package mk.ukim.finki.model.events;

import lombok.Getter;
import mk.ukim.finki.model.MenProduct;
import mk.ukim.finki.model.WomenProduct;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class WomenProductCreatedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public WomenProductCreatedEvent(WomenProduct source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public WomenProductCreatedEvent(MenProduct source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
