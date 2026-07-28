package ru.microservices.user_service.gRPC;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.microservices.common_events.proto.user.UserServiceGrpc;
import ru.microservices.common_events.proto.user.UserServiceOuterClass;
import ru.microservices.user_service.service.UserService;

@GrpcService
@AllArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase{

    private final UserService userService;

    public void checkEmailExistence(
            UserServiceOuterClass.CheckEmailExistenceRequest request,
            StreamObserver<UserServiceOuterClass.CheckEmailExistenceResponse> responseStreamObserver){

        try {
            boolean existence = userService.checkEmailExistence(request.getEmail());


            var response = UserServiceOuterClass.CheckEmailExistenceResponse.newBuilder()
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
