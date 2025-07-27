package geladeira.inteligente.MagicFrideAI.configur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

@Service
public class GemininService {

    private final RestTemplate restTemplate;
    private final String geminiApiKey;
    private final String endpoint;

    public GemininService(@Value("${gemini.api.key}") String geminiApiKey) {
        this.restTemplate = new RestTemplate();
        this.geminiApiKey = geminiApiKey;
        this.endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    }

    public String sugerirReceita(List<String> alimentos) {
        try {
            String prompt = "Sugira uma receita com os seguintes alimentos: " + String.join(", ", alimentos);
            System.out.println("📤 Prompt: " + prompt);

            Map<String, Object> part = Map.of("text", prompt);
            Map<String, Object> content = Map.of("parts", List.of(part));
            Map<String, Object> body = Map.of("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", this.geminiApiKey);  // <<< Chave no header

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> contentMap = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentMap.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        String result = (String) parts.get(0).get("text");
                        System.out.println("✅ Receita gerada: " + result);
                        return result;
                    }
                }
            }

            return "Nenhuma sugestão encontrada.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao sugerir receita com a IA: " + e.getMessage();
        }
    }
}
