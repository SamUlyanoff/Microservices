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
import ru.microservices.common_events.outbox.SentStatus;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthOutBoxScheduler {

    private final AuthOutBoxRepository authOutBoxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Отправка событий в брокер.<br></br>
     * Каждые 3 сек вытаскивает из БД не отправленные событие, статус которых не равен "FAILED".<p>
     * Отправка успешная - обновление статуса на 'SENT' и счетчика;<p>
     * Отправка неудачная:<p>
     * Счетчик попыток < 5 - просто обновление счетчика и попытка повторной отправки в будущем<p>
     * Счетчик попыток = 5 - обновление статуса на 'FAILED'
     */
    @Scheduled(fixedDelay = 5000)
    @Transactional(timeout = 5)
    public void sendingEvents() {

        List<AuthOutBoxEvent> events = authOutBoxRepository.getEventsWithoutSent();

        if (events.isEmpty()) {
            logger.info("Нет задач, ждущих отправки.");
            return;
        }

        logger.info("Количество не отправленных событий: {}", events.size());

        for (AuthOutBoxEvent event : events) {
            var tryCount = event.getTryCount();
            try {
                kafkaTemplate.send(
                        event.getEventType(),
                        event.getAggregateId(),
                        event.getPayload()
                ).get();

                authOutBoxRepository.updateSentStatus(
                        event.getId(),
                        true,
                        SentStatus.SENT,
                        tryCount + 1
                );
            } catch (Exception e) {
                logger.error("Ошибка при отправке события: eventId = {}, eventType = {}, error = {}", event.getId(), event.getEventType(), e.getMessage(), e);
                tryCount++;
                if (tryCount < 5) {
                    authOutBoxRepository.updateSentStatus(
                            event.getId(),
                            false,
                            SentStatus.PENDING,
                            tryCount
                    );
                }else {
                    authOutBoxRepository.updateSentStatus(
                            event.getId(),
                            false,
                            SentStatus.FAILED,
                            tryCount
                    );
                }
            }
        }
    }


}
