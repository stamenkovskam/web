package mk.ukim.finki.model.events;

import lombok.Getter;
import mk.ukim.finki.model.MenProduct;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class MenProductCreatedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public MenProductCreatedEvent(MenProduct source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public MenProductCreatedEvent(MenProduct source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
