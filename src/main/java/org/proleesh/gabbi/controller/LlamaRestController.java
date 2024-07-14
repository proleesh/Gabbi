package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.response.LlamaResponse;
import org.proleesh.gabbi.service.impl.LlamaAiServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author sung-hyuklee
 * date: 2024.7.14
 * Ollama Rest Controller
 */
@RestController
@RequiredArgsConstructor
public class LlamaRestController {

    private final LlamaAiServiceImpl llamaAiService;

    @GetMapping("/api/ai/generate")
    public ResponseEntity<LlamaResponse> generate(
            @RequestParam(value="promptMessage", defaultValue = "What is love?") String promptMessage
    ){
        final LlamaResponse aiResponse = llamaAiService.generateMessage(promptMessage);
        return ResponseEntity.status(HttpStatus.OK).body(aiResponse);
    }

    @PostMapping("/api/ai/generate/joke/{topic}")
    public ResponseEntity<LlamaResponse> generateJoke(@PathVariable String topic){
        final LlamaResponse aiResponse = llamaAiService.generateJoke(topic);
        return ResponseEntity.status(HttpStatus.OK).body(aiResponse);
    }

}
