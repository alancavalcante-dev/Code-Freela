package io.github.alancavalcante_dev.codefreelaapi.domain.generatedcommentia;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.gitea.ContentPatchCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;



@Service
public class GeneratorCommentIA {

    private final String username;
    private final String repository;
    private final Appointment appointment;

    @Autowired
    private ContentPatchCommit contentPatch;

    @Autowired
    private ArtificialIntelligencePrompt prompt;


    public GeneratorCommentIA(Appointment appointment) {
        this.username = appointment.getUser().getUsername();
        this.repository = appointment.getContainer().getName();
        this.appointment = appointment;
    }

    public String generete() {
        List<Map<String, Object>> commits = contentPatch.getCommits(username, repository);
        List<String> lastCommitsSha = contentPatch.getLastCommits(commits);
        String patch = contentPatch.getPatchCommits(username, repository, lastCommitsSha);
        return prompt.reviewCod(
                patch,
                appointment.getComment(),
                appointment.getDateStarting(),
                appointment.getDateClosing()
        );
    }


}


