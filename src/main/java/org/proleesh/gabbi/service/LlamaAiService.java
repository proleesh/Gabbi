package org.proleesh.gabbi.service;

import org.proleesh.gabbi.response.LlamaResponse;

public interface LlamaAiService {
    LlamaResponse generateMessage(String prompt);
    LlamaResponse generateJoke(String topic);
}
