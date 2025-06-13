package io.github.alancavalcante_dev.codefreelaapi.domain.container;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.WorkSession;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.WorkSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkSessionService {

    private final WorkSessionRepository repository;

    public WorkSession saveWorkSession(WorkSession workSession) {
        return repository.save(workSession);
    }


}
