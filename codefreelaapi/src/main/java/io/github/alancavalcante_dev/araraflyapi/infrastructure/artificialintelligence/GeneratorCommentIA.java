package io.github.alancavalcante_dev.araraflyapi.infrastructure.artificialintelligence;

import io.github.alancavalcante_dev.araraflyapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.gitea.commit.ContentPatchCommit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class GeneratorCommentIA {

    @Autowired
    private ContentPatchCommit contentPatch;


    @Autowired
    private ArtificialIntelligencePrompt prompt;


    public CompletableFuture<String> generate(Appointment appointment) {
        String username = appointment.getUser().getUsername();
        String repository = appointment.getContainer().getName();

        List<Map<String, Object>> commits = contentPatch.getCommits(username, repository);

        List<String> lastCommitsSha = contentPatch.getLastCommits(commits);
        lastCommitsSha.removeLast();

        String patch = contentPatch.getPatchCommits(username, repository, lastCommitsSha);

        return prompt.reviewCod(
                patch,
                appointment.getComment(),
                appointment.getDateStarting(),
                appointment.getDateClosing()
        );
    }
}



