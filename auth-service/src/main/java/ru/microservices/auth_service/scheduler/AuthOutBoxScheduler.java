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
import ru.microservices.auth_service.repository.UserPasswordRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@AllArgsConstructor
public class AuthOutBoxScheduler {

    private final AuthOutBoxRepository authOutBoxRepository;
    private final UserPasswordRepository userPasswordRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 3000)
    @Transactional(timeout = 5)
    public void sendingEvents() {

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

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void findingAndDeletingStuckEvents(){

        List<AuthOutBoxEvent> stuckEvents = authOutBoxRepository.getStuckEvents();

        if(stuckEvents.isEmpty()) return;

        logger.info("Количество зависших событий: {}", stuckEvents.size());

        for(AuthOutBoxEvent stuckEvent: stuckEvents){

            try {
                kafkaTemplate.send(
                        stuckEvent.getEventType(),
                        stuckEvent.getAggregateId(),
                        stuckEvent.getPayload()
                ).get(3, TimeUnit.SECONDS);

                authOutBoxRepository.updateSentStatusById(stuckEvent.getId(), true);

            }catch (Exception e) {
                UUID aggregateId = UUID.fromString(stuckEvent.getAggregateId());
                authOutBoxRepository.deleteByAggregateId(aggregateId);
                //TODO: наверное шедулер не должен знать про userPasswordRepository
                userPasswordRepository.deleteByUserId(aggregateId);
                logger.error("Вышло время для отправки сообщения: eventId = {}, eventType = {}. Производится удаление события.", stuckEvent.getId(), stuckEvent.getEventType());
            }
        }

    }

}
