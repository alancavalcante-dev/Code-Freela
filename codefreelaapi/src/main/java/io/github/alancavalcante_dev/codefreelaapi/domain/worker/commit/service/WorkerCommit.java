package io.github.alancavalcante_dev.codefreelaapi.domain.worker.commit.service;

import io.github.alancavalcante_dev.codefreelaapi.infrastructure.gitea.commit.ContentPatchCommit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerCommit {

    private final ContentPatchCommit patch;

    public List<String> getNameCommits(String username, String repository) {
        return patch.getCommits(username, repository)
                    .stream().map(commit -> (String) commit.get("sha"))
                    .toList();

    }

}
