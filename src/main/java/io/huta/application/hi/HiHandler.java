package io.huta.application.hi;

import io.huta.application.infra.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Slf4j
public class HiHandler {

    private Repository<Hi> repository;
    private ExecutorService executorService;

    public HiHandler(Repository<Hi> repository, ExecutorService executorService ) {
        this.repository = repository;
        this.executorService = executorService;
    }

    Mono<ServerResponse> respondWith200(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        log.debug("getById");
        Mono<String> id1 = Mono.justOrEmpty(request.pathVariable("id"));
        return id1
                .map(Integer::parseInt)
                .flatMap(id -> repository.getById(id))
                .flatMap(hi -> ServerResponse.ok().contentType(APPLICATION_JSON).syncBody(hi))
                .switchIfEmpty(ServerResponse.notFound().build())
                .subscribeOn(Schedulers.fromExecutorService(executorService));
    }

    public Mono<ServerResponse> put(ServerRequest request) {
        log.debug("put");
        Mono<CreateHiCommand> cmdMono = request.bodyToMono(CreateHiCommand.class);
        return cmdMono
                .flatMap(cmd -> repository.save(cmd.toHi()))
                .flatMap(saved -> ServerResponse
                        .accepted()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(saved))
                .subscribeOn(Schedulers.fromExecutorService(executorService)));
    }

    public Mono<ServerResponse> put2(ServerRequest request) {
        log.debug("put");
        Mono<CreateHiCommand> cmdMono = request.bodyToMono(CreateHiCommand.class);
        return cmdMono
                .flatMap(cmd -> save(cmd.toHi()))
                .subscribeOn(Schedulers.fromExecutorService(executorService));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.debug("delete");
        Mono<String> id1 = Mono.justOrEmpty(request.pathVariable("id"));
        return id1
                .map(Integer::parseInt)
                .flatMap(id -> repository.delete(id))
                .flatMap(hi -> ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build())
                .subscribeOn(Schedulers.fromExecutorService(executorService));
    }

    public Mono<ServerResponse> listAll(ServerRequest request) {
        log.debug("listAll");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(repository.listAll(), Hi.class)
                .subscribeOn(Schedulers.fromExecutorService(executorService));
    }

    private Mono<ServerResponse> save(Hi hi) {
        return repository.save(hi)
                .flatMap(saved -> ServerResponse
                        .accepted()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(saved))
                );
    }

}
