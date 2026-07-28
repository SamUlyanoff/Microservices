package ru.microservices.auth_service.gRPC;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.microservices.common_events.proto.user.*;

@Component
@AllArgsConstructor
public class GrpcUserClientService implements UserServiceClient {

    private final UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public Boolean checkEmailExistence(String email) {
        try {
            var request = UserServiceOuterClass.CheckEmailExistenceRequest.newBuilder()
                    .setEmail(email)
                    .build();

            var response = stub.checkEmailExistence(request);

            return response.getExistence();

        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка в обращении к user-service");
        }
    }
}
