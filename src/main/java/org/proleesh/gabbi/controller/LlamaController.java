package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.response.LlamaResponse;
import org.proleesh.gabbi.service.impl.LlamaAiServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author sung-hyuklee
 * date: 2024.7.14
 * Ollama Controller
 */
@Controller
@RequiredArgsConstructor
public class LlamaController {
    private final LlamaAiServiceImpl llamaAiService;

    @GetMapping("/ai/generate")
    public String generateForm(Model model,
                               @RequestParam(value="promptMessage", required=false) String promptMessage) {
        model.addAttribute("promptMessage", promptMessage);
        return "ai/llamaChat";
    }

    @PostMapping("/ai/generate")
    public String generateResponse(Model model, @RequestParam(value="promptMessage", required=false) String promptMessage) {
        LlamaResponse aiResponse = llamaAiService.generateMessage(promptMessage);
        model.addAttribute("aiResponse", aiResponse);
        return "ai/llamaChat";
    }
}
