package geladeira.inteligente.MagicFrideAI.configur;

import geladeira.inteligente.MagicFrideAI.model.FooItem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GemininService {

    private final RestTemplate restTemplate = new RestTemplate();


    private final String API_KEY = "AIzaSyDI4NGH1tM1If7YDf2gfNdRW16Z4bEkPv4"; // Substitua depois por variável de ambiente
    private final String GEMINI_ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;

    public String sugerirReceita(List<FooItem> alimentos) {
        // Monta o texto do prompt
        StringBuilder prompt = new StringBuilder("Sugira uma receita com os seguintes alimentos: ");
        for (FooItem item : alimentos) {
            prompt.append(item.getName()).append(", ");
        }

        // Corpo da requisição
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt.toString())
                        ))
                )
        );

        // Cabeçalhos HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria a requisição HTTP
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Envia requisição e obtém resposta
        ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_ENDPOINT, request, Map.class);

        // Verifica e processa a resposta
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("candidates")) {
            throw new RuntimeException("Resposta inválida da API Gemini");
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
        if (candidates.isEmpty()) {
            throw new RuntimeException("Nenhum candidato retornado pela API");
        }

        Map<String, Object> message = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, String>> parts = (List<Map<String, String>>) message.get("parts");

        if (parts == null || parts.isEmpty()) {
            throw new RuntimeException("Nenhuma parte retornada pela API");
        }

        return parts.get(0).get("text");
    }
}
