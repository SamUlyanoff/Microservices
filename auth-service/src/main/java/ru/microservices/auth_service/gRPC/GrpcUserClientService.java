package ru.microservices.auth_service.gRPC;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.microservices.common_events.proto.user.*;

@Component
public class GrpcUserClientService implements UserServiceClient {

    @GrpcClient("user-service")
    private UserInternalServiceGrpc.UserInternalServiceBlockingStub stub;

    @Override
    public Boolean checkEmailExistence(String email) {
        try {
            var request = UserService.CheckEmailExistenceRequest.newBuilder()
                    .setEmail(email)
                    .build();

            var response = stub.checkEmailExistence(request);

            return response.getExistence();

        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка в обращении к user-service" + e.getMessage(), e);
        }
    }
}
