package ru.microservices.user_service.gRPC;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.microservices.common_events.proto.user.UserInternalServiceGrpc;
import ru.microservices.user_service.service.UserService;

@GrpcService
@AllArgsConstructor
public class UserServiceImpl extends UserInternalServiceGrpc.UserInternalServiceImplBase {

    private final UserService userService;

    public void checkEmailExistence(
            ru.microservices.common_events.proto.user.UserService.CheckEmailExistenceRequest request,
            StreamObserver<ru.microservices.common_events.proto.user.UserService.CheckEmailExistenceResponse> responseStreamObserver){

        try {
            boolean existence = userService.checkEmailExistence(request.getEmail());


            var response = ru.microservices.common_events.proto.user.UserService.CheckEmailExistenceResponse.newBuilder()
                    .setExistence(existence)
                    .build();

            responseStreamObserver.onNext(response);

            responseStreamObserver.onCompleted();
        }catch (Exception e){
            responseStreamObserver.onError(
                    Status.INTERNAL
                        .withDescription("Ошибка выполнения checkEmailExistence" + e.getMessage())
                        .asRuntimeException()
            );
        }

    }
}
