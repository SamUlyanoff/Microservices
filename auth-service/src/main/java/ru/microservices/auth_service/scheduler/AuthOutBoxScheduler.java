package ru.microservices.auth_service.scheduler;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.auth_service.entity.AuthOutBoxEvent;
import ru.microservices.auth_service.repository.AuthOutBoxRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthOutBoxScheduler {

    private final AuthOutBoxRepository authOutBoxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 3000)
    @Transactional(timeout = 5)
    public void processOutbox() {

        List<AuthOutBoxEvent> events = authOutBoxRepository.getEventsWithoutSent();

        if (events.isEmpty()) {
            return;
        }

        logger.info("Количество не отправленных событий: {}", events.size());

        for (AuthOutBoxEvent event : events) {
            try {
                kafkaTemplate.send(
                        event.getEventType(),
                        event.getAggregateId(),
                        event.getPayload()
                ).get();

                authOutBoxRepository.updateSentStatusById(event.getId(), true);

            } catch (Exception e) {
                logger.error("Ошибка при отправке события: eventId = {}, eventType = {}, error = {}", event.getId(), event.getEventType(), e.getMessage(), e);
            }
        }
    }

}
