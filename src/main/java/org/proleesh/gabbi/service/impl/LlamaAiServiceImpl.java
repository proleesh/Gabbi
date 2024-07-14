package org.proleesh.gabbi.service.impl;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.response.LlamaResponse;
import org.proleesh.gabbi.service.LlamaAiService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlamaAiServiceImpl implements LlamaAiService {

    private final OllamaChatClient ollamaChatClient;

    @Override
    public LlamaResponse generateMessage(String prompt) {
        final String llamaMessage = ollamaChatClient.generate(prompt);
        return new LlamaResponse().setMessage(llamaMessage);
    }

    @Override
    public LlamaResponse generateJoke(String topic) {
        final String llamaMessage = ollamaChatClient.generate(String.format("Tell me a joke about %s", topic));
        return new LlamaResponse().setMessage(llamaMessage);
    }
}
